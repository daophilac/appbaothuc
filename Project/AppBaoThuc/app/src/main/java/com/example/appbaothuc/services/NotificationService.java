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
        String nextAlarm = databaseHandler.getNextAlarmFromNow();
        if(nextAlarm != null){
            String[] alarmContent = nextAlarm.split(",");
            Calendar timeNow = Calendar.getInstance();
            Calendar timeFuture = Calendar.getInstance();
            Calendar timeDelta = Calendar.getInstance();
            timeFuture.set(Calendar.HOUR_OF_DAY, Integer.parseInt(alarmContent[1]));
            timeFuture.set(Calendar.MINUTE, Integer.parseInt(alarmContent[2]));
            timeFuture.set(Calendar.SECOND, 0);
            long deltaInMillisecond = timeFuture.getTimeInMillis() - timeNow.getTimeInMillis();
            timeDelta.setTimeInMillis(timeNow.getTimeInMillis() + deltaInMillisecond);

            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            Intent alarmServiceIntent = new Intent(this.getApplicationContext(), AlarmService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, alarmServiceIntent, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeDelta.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Set alarm for " + alarmContent[0] + ", at " + alarmContent[1] + ":" + alarmContent[2], Toast.LENGTH_LONG).show();


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
                        .setContentTitle("Báo thức vào lúc:") // TODO
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
                        .setContentText("Báo thức vào lúc:")
                        .setContentIntent(pendingIntent)
                        .build();
                startForeground(NOTIFICATION_ID, notification);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}