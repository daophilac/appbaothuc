package com.example.appbaothuc.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.appbaothuc.Alarm;
import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.R;
import com.example.appbaothuc.challenge.ChallengeActivity;

import java.util.Calendar;

public class NotificationService extends Service {
    public static boolean DEBUG_MODE = false;
    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "com.example.appbaothuc";
    private static final String NOTIFICATION_CHANNEL_NAME = "App báo thức";
    private DatabaseHandler databaseHandler;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        databaseHandler = new DatabaseHandler(this);
        if(!databaseHandler.checkIfThereIsAnyAlarm()){
            stopService(new Intent(this, NotificationService.class));
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent alarmServiceIntent = new Intent(getApplicationContext(), ChallengeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, alarmServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        }
        else{
            Alarm alarm = databaseHandler.getTheNearestAlarm();
            Calendar timeNow = Calendar.getInstance();
            Calendar timeFuture = Calendar.getInstance();
            Calendar timeDelta = Calendar.getInstance();
            timeFuture.set(Calendar.DAY_OF_WEEK, alarm.getDayOfWeek());
            timeFuture.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            timeFuture.set(Calendar.MINUTE, alarm.getMinute());
            timeFuture.set(Calendar.SECOND, 0);
            long deltaInMillisecond = Math.abs(timeFuture.getTimeInMillis() - timeNow.getTimeInMillis());
            timeDelta.setTimeInMillis(timeNow.getTimeInMillis() + deltaInMillisecond);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent alarmServiceIntent = new Intent(getApplicationContext(), ChallengeActivity.class);
            byte[] byteAlarm = Alarm.toByteArray(alarm);
            alarmServiceIntent.putExtra("alarm", byteAlarm);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, alarmServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            if(DEBUG_MODE){
                Calendar temp = Calendar.getInstance();
                temp.add(Calendar.SECOND, 5);
                alarmManager.set(AlarmManager.RTC_WAKEUP, temp.getTimeInMillis(), pendingIntent);
            }
            else{
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeDelta.getTimeInMillis(), pendingIntent);
            }

            String nextAlarmTextDayOfWeek = databaseHandler.getDayOfWeekInString(timeFuture.get(Calendar.DAY_OF_WEEK));
            String nextAlarmTextTime;
            if(alarm.getMinute() < 10){
                nextAlarmTextTime = alarm.getHour() + ":0" + alarm.getMinute();
            }
            else{
                nextAlarmTextTime = alarm.getHour() + ":" + alarm.getMinute();
            }
            String nextAlarmText = nextAlarmTextDayOfWeek + " " + nextAlarmTextTime;


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
                notificationChannel.setLightColor(Color.BLUE);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                assert manager != null;
                manager.createNotificationChannel(notificationChannel);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
                Notification notification = notificationBuilder.setOngoing(true)
                        .setSmallIcon(R.drawable.ic_add) // TODO
                        .setContentTitle("[Next Alarm] " + nextAlarmText) // TODO
                        .setPriority(NotificationManager.IMPORTANCE_MIN)
                        .setCategory(Notification.CATEGORY_SERVICE)
                        .build();
                startForeground(NOTIFICATION_ID, notification);
            }
            else {
                Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                        .setOngoing(true)
                        .setSmallIcon(R.drawable.ic_add)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText("[Next Alarm] " + nextAlarmText)
                        .setContentIntent(pendingIntent)
                        .build();
                startForeground(NOTIFICATION_ID, notification);
            }
            Toast.makeText(this, "Set alarm at " + nextAlarmText, Toast.LENGTH_LONG).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}