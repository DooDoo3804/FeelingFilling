# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Chatting(models.Model):
    chatting_id = models.AutoField(primary_key=True)
    user = models.ForeignKey('User', models.DO_NOTHING)
    content = models.CharField(max_length=200)
    chat_date = models.DateTimeField()
    type = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'chatting'


class React(models.Model):
    react_id = models.AutoField(primary_key=True)
    chatting = models.ForeignKey(Chatting, models.DO_NOTHING)
    content = models.CharField(max_length=200)
    emotion = models.CharField(max_length=20)
    amount = models.PositiveIntegerField()

    class Meta:
        managed = False
        db_table = 'react'


class Report(models.Model):
    report_id = models.AutoField(primary_key=True)
    user = models.ForeignKey('User', models.DO_NOTHING)
    content = models.CharField(max_length=200)

    class Meta:
        managed = False
        db_table = 'report'


class Request(models.Model):
    request_id = models.AutoField(primary_key=True)
    user = models.ForeignKey('User', models.DO_NOTHING)
    content = models.CharField(max_length=200)
    request_time = models.DateTimeField()
    translation = models.CharField(max_length=200)
    react = models.CharField(max_length=200, blank=True, null=True)
    emotion = models.CharField(max_length=20, blank=True, null=True)
    intensity = models.FloatField(blank=True, null=True)
    amount = models.PositiveIntegerField(blank=True, null=True)
    success = models.PositiveIntegerField()

    class Meta:
        managed = False
        db_table = 'request'


class User(models.Model):
    user_id = models.PositiveIntegerField(primary_key=True)
    nickname = models.CharField(max_length=20)
    role = models.CharField(max_length=20)
    minimum = models.IntegerField()
    maximum = models.IntegerField()
    join_date = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'user'


class UserBadge(models.Model):
    user_badge_id = models.AutoField(primary_key=True)
    user = models.ForeignKey(User, models.DO_NOTHING)
    badge_id = models.PositiveIntegerField()
    achived_date = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'user_badge'
