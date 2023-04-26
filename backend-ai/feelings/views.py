import json
import requests
import time
import datetime
import tensorflow as tf
import logging

from time import sleep
from transformers import pipeline
from googletrans import Translator
from django.http import HttpResponse, JsonResponse
from rest_framework.decorators import api_view
from rest_framework.status import HTTP_201_CREATED, HTTP_200_OK
from apscheduler.schedulers.background import BackgroundScheduler
from .models import React, Chatting, Request, User

jwt_token = ""


# 텍스트 번역 api
# post 요청
@api_view(['POST'])
def analysis_text(request):
    # 받아온 text 데이터
    start = time.time()
    text = request.data['TEXT']
    
    # chatting 저장
    user = User.objects.get(user_id = 1)
    chatting = Chatting(user = user, content = text, chat_date = datetime.datetime.now(), type = 1)
    chatting.save()

    feeling, score, trans = translation_text(text)
    amount = cal_deposit(score)

    # react 데이터 저장 (GPT 답변)
    react = React(chatting = chatting, content = "GPT 답변!", emotion = feeling, amount = amount)
    react.save()

    # request 데이터 저장 (success 받아와야 함)
    request = Request(user = user, content = text, request_time = datetime.datetime.now(),
                      translation = trans, react = "GPT 답변", emotion = feeling, intensity = score,
                      amount = amount, success = 1)
    request.save()

    end = time.time()
    due_time = str(datetime.timedelta(seconds=(end-start))).split(".")
    print(f"소요시간 : {due_time}")
    return HttpResponse(status=201)


# 음성 번역 api
# post 요청
@api_view(['POST'])
def analysis_voice(request):
    start = time.time()
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
                    # 응답이 오지 않은 경우 1촣 재요청
                    print(f'Retrying after 1 seconds...')
                    sleep(1)
            print(resp.json())
        else : print("파일 없음!")
    except Exception as e:
        print(e)


    # chatting 저장
    user = User.objects.get(user_id = 1)
    chatting = Chatting(user = user, content = resp.json(), chat_date = datetime.datetime.now(), type = 1)
    chatting.save()

    # 번역 요청
    feeling, score, trans = translation_text(resp.json())

    # 적금 금액 계산
    amount = cal_deposit(score)

    # react 데이터 저장 (GPT 답변)
    react = React(chatting = chatting, content = "GPT 답변!", emotion = feeling, amount = amount)
    react.save()

    # request 데이터 저장 (success 받아와야 함)
    request = Request(user = user, content = resp.json(), request_time = datetime.datetime.now(),
                      translation = trans, react = "GPT 답변", emotion = feeling, intensity = score,
                      amount = amount, success = 1)
    request.save()

    end = time.time()
    due_time = str(datetime.timedelta(seconds=(end-start))).split(".")
    print(f"소요시간 : {due_time}")
    context = {
        "react" : "GPT 답변",
        "emotion" : feeling,
        "amount" : amount,
        "success" : 1
    }
    return JsonResponse(context, status = 201)


# 측정된 감정 정도에 따라 적금 금액 계산
def cal_deposit(score):
    user= User.objects.get(user_id = 1)
    min, max = user.minimum, user.maximum
    amount = round((max - min + 1) * score)
    return amount

def make_react():
    pass

# 번역 함수
def translation_text(text):
    google = Translator()
    text = text

    abs_translator = google.translate(text, dest="en")

    print("----------------------------------------------------------------")
    print("TRANSLATION")
    print(f"""{text}
    *****************************************************************
    {abs_translator.text}""")
    print("----------------------------------------------------------------")

    return analysis_emition(abs_translator)


# 감정 분석 함수
def analysis_emition(translation_result):
    classifier = pipeline("text-classification",
                          model='bhadresh-savani/distilbert-base-uncased-emotion', return_all_scores=True)
    prediction = classifier(translation_result.text)
    print("----------------------------------------------------------------")
    print("TOTAL PREDICTIONS")
    print(prediction)
    print("----------------------------------------------------------------")

    # 최대 감정과 감정 스코어 출력
    max_feeling = ''
    max_score = 0

    for p in prediction[0]:
        if p['score'] > max_score:
            max_feeling = p['label']
            max_score = p['score']

    print("----------------------------------------------------------------")
    print("MAX EMOTION")
    print(max_feeling, max_score)
    print("----------------------------------------------------------------")
    return max_feeling, max_score, translation_result.text


# GPU 가속 함수
def acc_gpu():
    # GPU 사용 가능한지 확인
    print("GPU is", "available" if tf.config.list_physical_devices(
        "GPU") else "NOT AVAILABLE")
    # GPU 가속 활성화
    gpus = tf.config.experimental.list_physical_devices('GPU')
    if gpus:
        try:
            # Currently, memory growth needs to be the same across GPUs
            for gpu in gpus:
                tf.config.experimental.set_memory_growth(gpu, True)
            logical_gpus = tf.config.experimental.list_logical_devices('GPU')
            print(len(gpus), "Physical GPUs,", len(
                logical_gpus), "Logical GPUs")
        except RuntimeError as e:
            # Memory growth must be set before GPUs have been initialized
            print(e)


# 초기에 모델을 받는데 시간이 오래걸림 // 초기 세팅 함수
def init_setting():
    get_jwt()
    classifier = pipeline("text-classification",
                          model='bhadresh-savani/distilbert-base-uncased-emotion', return_all_scores=True)
    classifier("")


"""
    인증 요청
    token의 만료 기간은 6시간
    주기적으로 token이 갱신될 수 있도록 /v1/authenticate 을 통해 token을 갱신해야 합니다.
    갱신은 스케줄링을 통해 작성
    client_id / client_secret 환경변수로 빼거나 따로 작성!!
"""


def get_jwt():
    global jwt_token
    print("start schd")
    resp = requests.post(
        'https://openapi.vito.ai/v1/authenticate',
        data={'client_id': "cnmeuourK_cZS7UMpGwG",
              'client_secret': "8XHGuP-vT3HJNK0R9zfxeK97eciLUcHF5jPKyhsz"}
    )
    resp.raise_for_status()
    jwt_token = resp.json()['access_token']
    print(resp.json()['access_token'])


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

# gpu 가속 함수 추가
acc_gpu()
