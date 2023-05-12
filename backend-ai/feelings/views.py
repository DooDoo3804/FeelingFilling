import json
import requests
import time
import datetime
import logging
import jwt
import openai
from bson import ObjectId
from FEELINGFILLING_DJANGO import settings
from pymongo import MongoClient
from time import sleep
from transformers import pipeline
from googletrans import Translator
from django.http import HttpResponse, JsonResponse
from rest_framework.decorators import api_view
from rest_framework.status import HTTP_201_CREATED, HTTP_200_OK
from apscheduler.schedulers.background import BackgroundScheduler
from .models import Request, User
from django.conf import settings

"""
    text 분석 요청 api
"""

jwt_token = ""

# 텍스트 번역 api
# post 요청
@api_view(['POST'])
def analysis_text(request):
    # 받아온 text 데이터
    start = time.time()
    # 토큰 decode해서 userid 추출
    try:
        token = request.headers.get('Authorization', None)[6::]
    except Exception as e:
        print(e)
        return HttpResponse(status=401, content='Authentication failed')
    user_id = decode_jwt_token(token)
    if not user_id:
            # jwt 검증 실패시 예외 처리
            return HttpResponse(status=401, content='Authentication failed')
    
    text = request.data['text']
    
    user = User.objects.get(user_id = user_id)
    # user = User.objects.get(user_id = user_id)

    """
        번역 요청
    """
    feeling, score, trans = translation_text(text)
    """
        적금 금액 계산
    """
    amount = cal_deposit(score, user_id)

    """
        react 데이터 저장 (GPT 답변)
        일단 주석
    """
    # react = React(chatting = chatting, content = "GPT 답변!", emotion = feeling, amount = amount)
    # react.save()
    gpt_react = make_react(trans)
    """
        billing 요청
        0 // 1
    """
    success, message = req_billing(token, amount, user_id)
    print(message)
    # success = req_billing(token, amount, user_id)
    if success:
        # request 데이터 저장 (success 받아와야 함)
        request = Request(user = user, content = text, request_time = datetime.datetime.now(),
                        translation = trans, react = gpt_react, emotion = feeling, intensity = score,
                        amount = amount, success = success)
        request.save()
        context = {
            "react" : gpt_react,
            "emotion" : feeling,
            "amount" : amount,
            "success" : success
        }
    else :
        context = {
            "react" : "ERROR",
            "emotion" : 0,
            "amount" : 0,
            "success" : success
        }

    check_chatting(user_id, gpt_react, feeling, amount, success)

    end = time.time()
    due_time = str(datetime.timedelta(seconds=(end-start))).split(".")
    print(f"소요시간 : {due_time}")
    return JsonResponse(context, status = 201, content_type=u"application/json; charset=utf-8")


"""
    voice 분석 요청 api
"""
# 음성 번역 api
# post 요청
@api_view(['POST'])
def analysis_voice(request):
    start = time.time()
    # 토큰 decode해서 userid 추출
    token = request.headers.get('Authorization', None)[6::]
    print(token)
    user_id = decode_jwt_token(token)
    if not user_id:
            # jwt 검증 실패시 예외 처리
            return HttpResponse(status=401, content='Authentication failed')
    
    """
        STT 요청
    """
    try:
        # 파일 받기
        voice = request.FILES.get('file')
        if voice is not None:
            config = {
                "diarization": {
                    "use_verification": False
                },
                "use_multi_channel": False
            }
            # 파일 TTS 요청
            resp = requests.post(
                'https://openapi.vito.ai/v1/transcribe',
                headers={'Authorization': 'bearer ' + jwt_token},
                data={'config': json.dumps(config)},
                files={'file': (voice.name, voice.read())},
            )
            resp.raise_for_status()
            id = resp.json()['id']
            # TTS 결과값 받기
            while True:
                resp = requests.get(
                    'https://openapi.vito.ai/v1/transcribe/'+id,
                    headers={'Authorization': 'bearer '+jwt_token},
                )
                resp.raise_for_status()
                if resp.json()['status'] == "completed":
                    # 응답이 성공적으로 온 경우
                    break
                else:
                    # 응답이 오지 않은 경우 1초 후 재요청
                    print(f'Retrying after 1 seconds...')
                    sleep(1)
            print(resp.json())
            
        else : print("파일 없음!")
    except Exception as e:
        print(e)
    text = resp.json()

    user = User.objects.get(user_id = user_id)

    """
        번역 요청
    """
    feeling, score, trans = translation_text(resp.json())

    """
        적금 금액 계산
    """
    amount = cal_deposit(score)
    
    """
        react 데이터 저장 (GPT 답변)
    """
    # react = React(chatting = chatting, content = "GPT 답변!", emotion = feeling, amount = amount)
    # react.save()
        
    # mongo db 조회해서 userid로 조회 및 count가 0 이 아니라면
    # 뒤에서부터 해당 개수 F로 바꿔주고 0으로 전환
    # chatting에 react도 저장해야함??
    # 토큰 받아야함....?

    check_chatting(user_id)

    """
        billing 요청
    """
    success, message = req_billing(token, amount, user_id)
    print(message)
    # 성공한 경우
    if (success) :
        # request 데이터 저장 (success 받아와야 함)
        request = Request(user = user, content = resp.json(), request_time = datetime.datetime.now(),
                        translation = trans, react = "GPT 답변", emotion = feeling, intensity = score,
                        amount = amount, success = success)
        request.save()
        context = {
            "react" : "GPT 답변",
            "emotion" : feeling,
            "amount" : amount,
            "success" : success
        }
    # 실패한 경우
    else :
        request = Request(user = user, content = resp.json(), request_time = datetime.datetime.now(),
                translation = trans, react = "GPT 답변", emotion = "", intensity = 0,
                amount = 0, success = success)
        request.save()
        context = {
            "react" : 0,
            "emotion" : 0,
            "amount" : 0,
            "success" : success
        }


    end = time.time()
    due_time = str(datetime.timedelta(seconds=(end-start))).split(".")
    print(f"소요시간 : {due_time}")
    return JsonResponse(context, status = 201, content_type=u"application/json; charset=utf-8")


# 측정된 감정 정도에 따라 적금 금액 계산
def cal_deposit(score, user_id):
    user= User.objects.get(user_id = user_id)
    min, max = user.minimum, user.maximum
    amount = round((max - min + 1) * (score-0.333))
    print(amount)
    return amount


# GPT // ChatBot react 생성 함수
def make_react(text):
    print(text)
    prompt = text + ". 위로하거나 맞장구 쳐주는 말을 한국어로 해줘, 짧게 한 두 문장으로"
    print(prompt)
    openai.api_key = settings.OPEN_AI_API_KEY
    model_engine = "GPT-3.5-turbo"  # 대신에 "text-ada-002"를 사용할 수 있습니다.
    model_prompt = f"{prompt}\nModel: "
    completions = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=[
        {"role": "user", "content": prompt}
        ],
        temperature=0.7,
    )
    # completions = openai.ChatCompletion.create(
    #     engine=model_engine,
    #     prompt=model_prompt,
    #     max_tokens=500,
    #     n=1,
    #     stop=None,
    #     temperature=0.7,
    # )
    message = completions.choices[0].message.content
    print(message)
    return message


# chatting에 insert 함수
def check_chatting(user_id, gpt_react, feeling, amount, success):
    try:
        resp = requests.post(
            'http://13.124.31.137:8702/api/',
            headers = {
                "Content-Type": "application/json",
                "Authorization": "bearer " + jwt_token
            },
            json = {
                "react" : gpt_react,
                "emotion" : feeling,
                "amount" : amount,
                "success" : success
            }
        )
    except Exception as e:
        print(e)

    return 1
    # MongoDB 클라이언트 생성
    # client = MongoClient('mongodb://root:mammoth77@3.38.191.128:27017/?authMechanism=DEFAULT/')
    # # 데이터베이스 선택
    # db = client['feelingfilling']
    # # 컬렉션 선택
    # collection = db['senders']
    # ch_collection = db['chattings']

    # # 문서 생성 삽입
    # # chat = {"user": 1, "text": [text], "date": datetime.datetime.now(), "count" : 3}
    # # result = collection.insert_one(chat)

    # # # 단일 문서 조회
    # # 만약 count? 가 0이 아니라면 뒤에서 부터 이를 보두 F로 바꿔주고
    # # count를 0으로 바꿔준다.
    # # 이후 voice 분석 진행
    # # 비동기 처리?
    # post = collection.find_one({"_id": user_id})
    
    # last_date = post['lastDate']
    # now_date = datetime.datetime.now()
    # # 날짜 다른 경우 업데이트 및 chatting에 삽입

    # update_text = post['chattings']
    # if (post['numOfUnAnalysed'] != 0):
    #     for i in range(post['numOfUnAnalysed']):
    #          filter = {"_id" : ObjectId(update_text[len(post['chattings']) - i - 1].id) }
    #          update = { "$set" : {"isAnalysed" : True} }
    #          ch_collection.update_one(filter, update)

    # # db 업데이트
    # if (last_date.year != now_date.year or
    #     last_date.month != now_date.month or
    #     last_date.day != now_date.day) :

    #     filter = {"_id" : user_id}
    #     update1 = {'$set' : {'lastDate' : datetime.datetime.now()}}
    #     collection.update_one(filter, update1)

    #     chat = {
    #         "type": 3,
    #         "content" : datetime.datetime.now().strftime('%Y-%m-%d'),
    #         'mood' : "default",
    #         "amount" : 0,
    #         "userId" : user_id,
    #         "chatDate" : datetime.datetime.now(),
    #         "isAnalysed" : True,
    #     }
    #     ch_collection.insert_one(chat)

    # filter = {"_id" : user_id}
    # update1 = {'$set' : {'numOfUnAnalysed' : 0}}
    # collection.update_one(filter, update1)

    # update2 = {'$set' : {'numOfChat' : post['numOfChat'] + 1}}
    # collection.update_one(filter, update2)

    # chat = {
    #         "type": 0,
    #         "content" : text,
    #         'mood' : "default",
    #         "amount" : amount,
    #         "userId" : user_id,
    #         "chatDate" : datetime.datetime.now(),
    #         "isAnalysed" : True,
    # }
    # ch_collection.insert_one(chat)

    # # 다중 문서 조회
    # # for post in collection.find():
    # #     print(post)
    # return post


# 번역 함수
def translation_text(text):
    google = Translator()
    input_text = text
    print(input_text)
    abs_translator = google.translate(input_text, dest="en")

    print("----------------------------------------------------------------")
    print("TRANSLATION")
    print(f"""{input_text}
    *****************************************************************
    {abs_translator.text}""")
    print("----------------------------------------------------------------")

    return analysis_emotion(abs_translator)

# 감정 분석 함수
def analysis_emotion(translation_result): 
    # 번역
    classifier = pipeline("text-classification",
                          model='j-hartmann/emotion-english-distilroberta-base', return_all_scores=True)
    prediction = classifier(translation_result.text)
    print("----------------------------------------------------------------")
    print("TOTAL PREDICTIONS")
    print(prediction)
    print("----------------------------------------------------------------")

    # 최대 감정과 감정 스코어 출력
    max_feeling = ''
    max_score = 0

    scores = [0, 0, 0]
    feelings = ["joy", "sadness", "anger"]

    for p in prediction[0]:
        score = p['score']
        feeling = p['label']
        # anger joy sadness
        # disgust fear  neatral  surprise
        # 각 감정을 서비스 기준에 맞게 재집계
        if (feeling == "joy") : scores[0] += score
        elif (feeling == "sadness") : scores[1] += score
        elif (feeling == "anger") : scores[2] += score
        elif (feeling == "fear") :
            scores[1] += score * 0.35
            scores[2] += score * 0.65
        elif (feeling == "surprise") :
            scores[1] += score * 0.5
            scores[2] += score * 0.5
        elif (feeling == "disgust") :
            scores[1] += score * 0.65
            scores[2] += score * 0.35
        elif (feeling == "neutral") :
            scores[0] += score * 0.33
            scores[1] += score * 0.33
            scores[2] += score * 0.33

    # max 감정과 스코어 추출
    max_score = max(scores)
    max_feeling = feelings[scores.index(max(scores))]

    print("----------------------------------------------------------------")
    print("MAX EMOTION")
    print(max_feeling, max_score)
    print("----------------------------------------------------------------")
    return max_feeling, max_score, translation_result.text


# Billing에 요청 함수
# def req_billing(token, amount, user_id):
def req_billing(token, amount, user_id):
    try:
        resp = requests.post(
            'http://13.124.31.137:8702/billing/subscription',
            headers = {
                "Content-Type": "application/json",
                "Authorization": "bearer " + jwt_token
            },
            json={
                'amount' : amount,
                'serviceUserId': 1,
                'serviceName': "abcd",
            }
        )
        success = resp.json()['result']
        message = resp.json()['message']
    except Exception as e:
        print(e)
    return success, message


# jwt decode 함수
def decode_jwt_token(access_token):
    try:
        decoded_data = jwt.decode(access_token, options={"verify_signature": False})
        user_id = decoded_data.get('userId')
        return user_id
    except jwt.ExpiredSignatureError:
        # 토큰이 만료되었을 때 예외 처리
        return HttpResponse(status=401, content='Expired token')
    except jwt.InvalidTokenError:
        # 토큰이 올바르지 않을 때 예외 처리
        return HttpResponse(status=401, content='Invalid token')


# 초기에 모델을 받는데 시간이 오래걸림 // 초기 세팅 함수
def init_setting():
    get_jwt()
    classifier = pipeline("text-classification",
                          model='j-hartmann/emotion-english-distilroberta-base', return_all_scores=True)
    classifier("")


"""
    STT JWT TOKEN
    인증 요청
    token의 만료 기간은 6시간
    주기적으로 token이 갱신될 수 있도록 /v1/authenticate 을 통해 token을 갱신해야 합니다.
    갱신은 스케줄링을 통해 작성
    client_id / client_secret 환경변수로 빼거나 따로 작성!!
"""


def get_jwt():
    global jwt_token
    start = time.time()
    print("start schd")
    resp = requests.post(
        'https://openapi.vito.ai/v1/authenticate',
        data={'client_id': "cnmeuourK_cZS7UMpGwG",
              'client_secret': "8XHGuP-vT3HJNK0R9zfxeK97eciLUcHF5jPKyhsz"}
    )
    resp.raise_for_status()
    jwt_token = resp.json()['access_token']
    end = time.time()
    due_time = str(datetime.timedelta(seconds=(end-start))).split(".")
    print(f"[{datetime.datetime.now()}] 소요시간 : {due_time}")


def schedule_api():
    print("starting update JWT")
    sched = BackgroundScheduler()
    sched.add_job(get_jwt, 'interval', hours=5, minutes=30)
    try:
        sched.start()
    except Exception as e:
        logging.exception(f"Error in background job: {str(e)}")


# 맨 처음 실행
init_setting()

# 스케줄러 api 실행
schedule_api()