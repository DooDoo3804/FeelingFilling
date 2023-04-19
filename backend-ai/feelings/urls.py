from django.urls import path, include
from . import views

urlpatterns = [
    path('text/', views.analysis_text, name="analysis_text"),
    path('voice/', views.analysis_voice, name="analysis_voice"),
]