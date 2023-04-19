import tensorflow as tf
from django.shortcuts import render
from transformers import pipeline
from googletrans import Translator


# 텍스트 번역 api
# post 요청
def analysis_text(request):
    pass


def analysis_voice(request):
    pass


# 번역 함수
def translation_text():
    google = Translator()
    text = """
    왜 이렇게 도라이가 많은거야....
    """

    abs_translator = google.translate(text, dest="en")
    analysis_emition(abs_translator.text)
    print(abs_translator.text)


# 감정 분석 함수
def analysis_emition(translation_result):
    classifier = pipeline("text-classification",
                          model='bhadresh-savani/distilbert-base-uncased-emotion', return_all_scores=True)
    prediction = classifier(translation_result.text)
    print(prediction)

    # 최대 감정과 감정 스코어 출력
    max_feeling = ''
    max_score = 0

    for p in prediction[0]:
        if p['score'] > max_score:
            max_feeling = p['label']
            max_score = p['score']
    print(max_feeling)
    print(max_score)


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


acc_gpu()
