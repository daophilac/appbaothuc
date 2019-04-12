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
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.R;

import java.util.Calendar;
import java.util.HashMap;

public class NotificationService extends Service {
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
        if(databaseHandler.checkIfThereIsAnyAlarm()){
            HashMap<String, String> alarmProperty = databaseHandler.getTheNearestAlarm();
            Calendar timeNow = Calendar.getInstance();
            Calendar timeFuture = Calendar.getInstance();
            Calendar timeDelta = Calendar.getInstance();
            timeFuture.set(Calendar.DAY_OF_WEEK, Integer.parseInt(alarmProperty.get("DayOfWeek")));
            timeFuture.set(Calendar.HOUR_OF_DAY, Integer.parseInt(alarmProperty.get("Hour")));
            timeFuture.set(Calendar.MINUTE, Integer.parseInt(alarmProperty.get("Minute")));
            timeFuture.set(Calendar.SECOND, 0);
            long deltaInMillisecond = Math.abs(timeFuture.getTimeInMillis() - timeNow.getTimeInMillis());
            timeDelta.setTimeInMillis(timeNow.getTimeInMillis() + deltaInMillisecond);
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            Intent alarmServiceIntent = new Intent(this.getApplicationContext(), AlarmService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, alarmServiceIntent, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeDelta.getTimeInMillis(), pendingIntent);



            String nextAlarmTextDayOfWeek = databaseHandler.getDayOfWeekInString(timeFuture.get(Calendar.DAY_OF_WEEK));
            String nextAlarmTextTime;
            if(Integer.parseInt(alarmProperty.get("Minute")) < 10){
                nextAlarmTextTime = alarmProperty.get("Hour") + ":0" + alarmProperty.get("Minute");
            }
            else{
                nextAlarmTextTime = alarmProperty.get("Hour") + ":" + alarmProperty.get("Minute");
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