# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Badge(models.Model):
    userbadgeid = models.AutoField(db_column='UserBadgeId', primary_key=True)  # Field name made lowercase.
    achieveddate = models.DateTimeField(db_column='achievedDate', blank=True, null=True)  # Field name made lowercase.
    badgeid = models.IntegerField(db_column='badgeId')  # Field name made lowercase.
    user_user = models.ForeignKey('User', models.DO_NOTHING, blank=True, null=True)
    user_userid = models.CharField(db_column='user_userId', max_length=255, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Badge'


class Chatting(models.Model):
    chatting_id = models.AutoField(primary_key=True)
    chat_date = models.DateTimeField(blank=True, null=True)
    content = models.CharField(max_length=255, blank=True, null=True)
    type = models.SmallIntegerField()
    user = models.ForeignKey('User', models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'Chatting'


class React(models.Model):
    react_id = models.AutoField(primary_key=True)
    amount = models.IntegerField()
    content = models.CharField(max_length=255, blank=True, null=True)
    emotion = models.CharField(max_length=255, blank=True, null=True)
    chatting = models.ForeignKey(Chatting, models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'React'


class Request(models.Model):
    requestid = models.AutoField(db_column='requestId', primary_key=True)  # Field name made lowercase.
    amount = models.IntegerField()
    content = models.CharField(max_length=255, blank=True, null=True)
    emotion = models.CharField(max_length=255, blank=True, null=True)
    intensity = models.FloatField(blank=True, null=True)
    react = models.CharField(max_length=255, blank=True, null=True)
    requesttime = models.DateTimeField(db_column='requestTime', blank=True, null=True)  # Field name made lowercase.
    success = models.TextField()  # This field type is a guess.
    translation = models.CharField(max_length=255, blank=True, null=True)
    user_user = models.ForeignKey('User', models.DO_NOTHING, blank=True, null=True)
    user_userid = models.CharField(db_column='user_userId', max_length=255, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Request'


class User(models.Model):
    user_id = models.AutoField(primary_key=True)
    id_oauth2 = models.CharField(max_length=255, blank=True, null=True)
    join_date = models.DateTimeField(blank=True, null=True)
    maximum = models.IntegerField()
    minimum = models.IntegerField()
    nickname = models.CharField(max_length=255, blank=True, null=True)
    role = models.CharField(max_length=255, blank=True, null=True)
    userid = models.CharField(db_column='userId', max_length=255)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'User'


class AuthGroup(models.Model):
    name = models.CharField(unique=True, max_length=150)

    class Meta:
        managed = False
        db_table = 'auth_group'


class AuthGroupPermissions(models.Model):
    id = models.BigAutoField(primary_key=True)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)
    permission = models.ForeignKey('AuthPermission', models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_group_permissions'
        unique_together = (('group', 'permission'),)


class AuthPermission(models.Model):
    name = models.CharField(max_length=255)
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING)
    codename = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'auth_permission'
        unique_together = (('content_type', 'codename'),)


class AuthUser(models.Model):
    password = models.CharField(max_length=128)
    last_login = models.DateTimeField(blank=True, null=True)
    is_superuser = models.IntegerField()
    username = models.CharField(unique=True, max_length=150)
    first_name = models.CharField(max_length=150)
    last_name = models.CharField(max_length=150)
    email = models.CharField(max_length=254)
    is_staff = models.IntegerField()
    is_active = models.IntegerField()
    date_joined = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'auth_user'


class AuthUserGroups(models.Model):
    id = models.BigAutoField(primary_key=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_groups'
        unique_together = (('user', 'group'),)


class AuthUserUserPermissions(models.Model):
    id = models.BigAutoField(primary_key=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    permission = models.ForeignKey(AuthPermission, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_user_permissions'
        unique_together = (('user', 'permission'),)


class DjangoAdminLog(models.Model):
    action_time = models.DateTimeField()
    object_id = models.TextField(blank=True, null=True)
    object_repr = models.CharField(max_length=200)
    action_flag = models.PositiveSmallIntegerField()
    change_message = models.TextField()
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING, blank=True, null=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'django_admin_log'


class DjangoContentType(models.Model):
    app_label = models.CharField(max_length=100)
    model = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'django_content_type'
        unique_together = (('app_label', 'model'),)


class DjangoMigrations(models.Model):
    id = models.BigAutoField(primary_key=True)
    app = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    applied = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_migrations'


class DjangoSession(models.Model):
    session_key = models.CharField(primary_key=True, max_length=40)
    session_data = models.TextField()
    expire_date = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_session'


class Report(models.Model):
    report_id = models.AutoField(primary_key=True)
    user = models.ForeignKey(User, models.DO_NOTHING)
    content = models.CharField(max_length=200)

    class Meta:
        managed = False
        db_table = 'report'


class UserBadge(models.Model):
    user_badge_id = models.AutoField(primary_key=True)
    user = models.ForeignKey(User, models.DO_NOTHING)
    badge_id = models.PositiveIntegerField()
    achived_date = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'user_badge'
