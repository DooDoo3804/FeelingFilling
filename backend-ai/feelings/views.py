import json
import requests
import tensorflow as tf
import logging

from time import sleep
from transformers import pipeline
from googletrans import Translator
from rest_framework.decorators import api_view
from rest_framework.status import HTTP_201_CREATED, HTTP_200_OK
from apscheduler.schedulers.background import BackgroundScheduler

# 텍스트 번역 api
# post 요청


@api_view(['POST'])
def analysis_text(request):
    text = request.POST.get("TEXT")
    translation_text(text)


# 음성 번역 api
# post 요청
@api_view(['POST'])
def analysis_voice(request):
    text = request.POST.get("TEXT")
    pass


# 번역 함수
def translation_text(text):
    google = Translator()
    text = text

    abs_translator = google.translate(text, dest="en")
    analysis_emition(abs_translator)

    print("----------------------------------------------------------------")
    print("TRANSLATION")
    print(f"""{text}
    *****************************************************************
    {abs_translator.text}""")
    print("----------------------------------------------------------------")


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


"""
    인증 요청
    token의 만료 기간은 6시간입니다.
    주기적으로 token이 갱신될 수 있도록 /v1/authenticate 을 통해 token을 갱신해야 합니다.
    갱신은 스케줄링을 통해 작성
    client_id / client_secret 환경변수로 빼거나 따로 작성!!
"""


def get_jwt():
    resp = requests.post(
        'https://openapi.vito.ai/v1/authenticate',
        data={'client_id': "cnmeuourK_cZS7UMpGwG",
              'client_secret': "8XHGuP-vT3HJNK0R9zfxeK97eciLUcHF5jPKyhsz"}
    )
    resp.raise_for_status()
    print(resp.json()['access_token'])


def schedule_api():
    print("starting update JWT")
    sched = BackgroundScheduler()
    sched.add_job(get_jwt, 'interval', minutes=2)
    try:
        sched.start()
    except Exception as e:
        logging.exception(f"Error in background job: {str(e)}")


schedule_api()
acc_gpu()
