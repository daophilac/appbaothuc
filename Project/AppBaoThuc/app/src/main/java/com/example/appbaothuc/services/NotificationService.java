package com.example.appbaothuc.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.MainActivity;
import com.example.appbaothuc.R;
import com.example.appbaothuc.appsetting.AppSettingFragment;
import com.example.appbaothuc.challenge.ChallengeActivity;
import com.example.appbaothuc.models.Alarm;

import java.util.Calendar;

public class NotificationService extends Service {
    public static boolean DEBUG_MODE = false;
    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "com.example.appbaothuc";
    private static final String NOTIFICATION_CHANNEL_NAME = "AppBaoThuc";
    private DatabaseHandler databaseHandler;
    private Alarm alarm;
    private Intent intentMainActivity;
    private Intent intentChallengeActivity;
    private PendingIntent pendingIntentMainActivity;
    private PendingIntent pendingIntentChallengeActivity;
    private AlarmManager alarmManager;
    private Notification notification;
    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;
    private NotificationCompat.Builder notificationBuilder;
    private Calendar timeNow;
    private Calendar timeFuture;
    private Calendar timeDelta;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(new ActionReceiver(), intentFilter);
        databaseHandler = new DatabaseHandler(this);
        intentMainActivity = new Intent(this, MainActivity.class);
        intentChallengeActivity = new Intent(getApplicationContext(), ChallengeActivity.class);
        pendingIntentMainActivity = PendingIntent.getActivity(this, REQUEST_CODE, intentMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if("changeHourMode".equals(intent.getAction())){
            if(alarm != null){
                String nextAlarmTextDayOfWeek = databaseHandler.getDayOfWeekInString(timeFuture.get(Calendar.DAY_OF_WEEK));
                String nextAlarmTextTime = buildTimeInString();
                String nextAlarmText = nextAlarmTextDayOfWeek + " " + nextAlarmTextTime;
                notification = notificationBuilder.setOngoing(true)
                        .setCategory(Notification.CATEGORY_SERVICE)
                        .setSmallIcon(R.drawable.ic_add) // TODO
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText("[Next Alarm] " + nextAlarmText)
                        .setContentIntent(pendingIntentMainActivity)
                        .build();
                startForeground(NOTIFICATION_ID, notification);
            }
        }
        if("updatePendingAlarm".equals(intent.getAction())){
            if(!databaseHandler.checkIfThereIsAnyAlarm()){
                if(pendingIntentChallengeActivity != null){
                    alarmManager.cancel(pendingIntentChallengeActivity);
                }
                stopSelf();
            }
            else{
                alarm = databaseHandler.getTheNearestAlarm();
                alarm.validateRingtoneUrl(this);
                timeNow = Calendar.getInstance();
                timeFuture = Calendar.getInstance();
                timeDelta = Calendar.getInstance();
                timeFuture.set(Calendar.DAY_OF_WEEK, alarm.getDayOfWeek());
                timeFuture.set(Calendar.HOUR_OF_DAY, alarm.getHour());
                timeFuture.set(Calendar.MINUTE, alarm.getMinute());
                timeFuture.set(Calendar.SECOND, 0);
                long deltaInMillisecond = Math.abs(timeFuture.getTimeInMillis() - timeNow.getTimeInMillis());
                timeDelta.setTimeInMillis(timeNow.getTimeInMillis() + deltaInMillisecond);
                byte[] byteAlarm = Alarm.toByteArray(alarm);
                intentChallengeActivity.putExtra("byteAlarm", byteAlarm);
                pendingIntentChallengeActivity = PendingIntent.getActivity(this, REQUEST_CODE, intentChallengeActivity, PendingIntent.FLAG_UPDATE_CURRENT);
                if(DEBUG_MODE){
                    Calendar temp = Calendar.getInstance();
                    temp.add(Calendar.SECOND, 5);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, temp.getTimeInMillis(), pendingIntentChallengeActivity);
                }
                else{
                    alarmManager.set(AlarmManager.RTC_WAKEUP, timeDelta.getTimeInMillis(), pendingIntentChallengeActivity);
                }
                String nextAlarmTextDayOfWeek = databaseHandler.getDayOfWeekInString(timeFuture.get(Calendar.DAY_OF_WEEK));
                String nextAlarmTextTime = buildTimeInString();
                String nextAlarmText = nextAlarmTextDayOfWeek + " " + nextAlarmTextTime;
                notification = notificationBuilder.setOngoing(true)
                        .setCategory(Notification.CATEGORY_SERVICE)
                        .setSmallIcon(R.drawable.ic_add) // TODO
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText("[Next Alarm] " + nextAlarmText)
                        .setContentIntent(pendingIntentMainActivity)
                        .build();
                startForeground(NOTIFICATION_ID, notification);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
    private String buildTimeInString(){
        if(AppSettingFragment.hourMode == AppSettingFragment.HOUR_MODE_12){
            if(alarm.getHour() == 0){
                return 12 + String.format(":%02d AM", alarm.getMinute());
            }
            else if(alarm.getHour() < 12){
                return alarm.getHour() + String.format(":%02d AM", alarm.getMinute());
            }
            else if(alarm.getHour() == 12){
                return alarm.getHour() + String.format(":%02d PM", alarm.getMinute());
            }
            else{
                return (alarm.getHour() - 12) + String.format(":%02d PM", alarm.getMinute());
            }
        }
        else{
            return alarm.getHour() + String.format(":%02d", alarm.getMinute());
        }
    }
    public static void update(Context context){
        Intent intentNotification = new Intent(context, NotificationService.class);
        intentNotification.setAction("updatePendingAlarm");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(intentNotification);
            context.startService(intentNotification);
        }
        else{
            context.startService(intentNotification);
        }
    }
    public static void changeHourMode(Context context){
        Intent intentNotification = new Intent(context, NotificationService.class);
        intentNotification.setAction("changeHourMode");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(intentNotification);
            context.startService(intentNotification);
        }
        else{
            context.startService(intentNotification);
        }
    }
}