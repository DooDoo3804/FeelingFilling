from django.urls import path, include
from . import views

urlpatterns = [
    path('text', views.analysis_text),
    path('voice', views.analysis_voice),
]
