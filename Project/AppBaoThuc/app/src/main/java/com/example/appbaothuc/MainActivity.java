package com.example.appbaothuc;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.appbaothuc.services.AlarmService;
import com.example.appbaothuc.services.NotificationService;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "APPBAOTHUC.db";
    private DatabaseHandler databaseHandler;
    private ImageButton buttonAlarm;
    private ImageButton buttonSetting;
    private UpcomingAlarmFragment upcomingAlarmFragment = new UpcomingAlarmFragment();
    private SettingFragment settingFragment = new SettingFragment();
    private FragmentManager fragmentManager;
    private MediaPlayer musicPlayer;

    private boolean settingFragmentIsAdded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this, DATABASE_NAME, null, 1);


        // TODO: debug purpose
//        Calendar time1 = Calendar.getInstance();
//        Calendar time2 = Calendar.getInstance();
//        Calendar time3 = Calendar.getInstance();
//        Calendar time4 = Calendar.getInstance();
//        Calendar time5 = Calendar.getInstance();
//        Calendar time6 = Calendar.getInstance();
//        Calendar time7 = Calendar.getInstance();
//        Calendar time8 = Calendar.getInstance();
//        Calendar time9 = Calendar.getInstance();
//        Calendar time0 = Calendar.getInstance();
//        time1.add(Calendar.MINUTE, 5);
//        time2.add(Calendar.MINUTE, 3);
//        time3.add(Calendar.MINUTE, 1);
//        time4.add(Calendar.MINUTE, 4);
//        time5.add(Calendar.MINUTE, 2);
//        time6.add(Calendar.MINUTE, 6);
//        time7.add(Calendar.MINUTE, 7);
//        time8.add(Calendar.MINUTE, 8);
//        time9.add(Calendar.MINUTE, 9);
//        time0.add(Calendar.MINUTE, 10);
//        int hour1 = time1.get(Calendar.HOUR_OF_DAY);
//        int hour2 = time2.get(Calendar.HOUR_OF_DAY);
//        int hour3 = time3.get(Calendar.HOUR_OF_DAY);
//        int hour4 = time4.get(Calendar.HOUR_OF_DAY);
//        int hour5 = time5.get(Calendar.HOUR_OF_DAY);
//        int hour6 = time6.get(Calendar.HOUR_OF_DAY);
//        int hour7 = time7.get(Calendar.HOUR_OF_DAY);
//        int hour8 = time8.get(Calendar.HOUR_OF_DAY);
//        int hour9 = time9.get(Calendar.HOUR_OF_DAY);
//        int hour0 = time0.get(Calendar.HOUR_OF_DAY);
//        int minute1 = time1.get(Calendar.MINUTE);
//        int minute2 = time2.get(Calendar.MINUTE);
//        int minute3 = time3.get(Calendar.MINUTE);
//        int minute4 = time4.get(Calendar.MINUTE);
//        int minute5 = time5.get(Calendar.MINUTE);
//        int minute6 = time6.get(Calendar.MINUTE);
//        int minute7 = time7.get(Calendar.MINUTE);
//        int minute8 = time8.get(Calendar.MINUTE);
//        int minute9 = time9.get(Calendar.MINUTE);
//        int minute0 = time0.get(Calendar.MINUTE);
//        List<Integer> listRepeatDay1 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
//        List<Integer> listRepeatDay2 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
//        List<Integer> listRepeatDay3 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
//        List<Integer> listRepeatDay4 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
//        List<Integer> listRepeatDay5 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
//        List<Integer> listRepeatDay6 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
//        List<Integer> listRepeatDay7 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
//        List<Integer> listRepeatDay8 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
//        List<Integer> listRepeatDay9 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
//        List<Integer> listRepeatDay0 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
//        databaseHandler.insertAlarm(true, hour1, minute1, listRepeatDay1);
//        databaseHandler.insertAlarm(true, hour2, minute2, listRepeatDay2);
//        databaseHandler.insertAlarm(true, hour3, minute3, listRepeatDay3);
//        databaseHandler.insertAlarm(true, hour4, minute4, listRepeatDay4);
//        databaseHandler.insertAlarm(true, hour5, minute5, listRepeatDay5);
//        databaseHandler.insertAlarm(true, hour6, minute6, listRepeatDay6);
//        databaseHandler.insertAlarm(true, hour7, minute7, listRepeatDay7);
//        databaseHandler.insertAlarm(true, hour8, minute8, listRepeatDay8);
//        databaseHandler.insertAlarm(true, hour9, minute9, listRepeatDay9);
//        databaseHandler.insertAlarm(true, hour0, minute0, listRepeatDay0);
//        Intent intent = new Intent(this, NotificationService.class);
//        startService(intent);


//        Intent intent = new Intent(this, ChallengeActivity.class);
//        startActivity(intent);
        // TODO



        buttonAlarm = findViewById(R.id.button_alarm);
        buttonSetting = findViewById(R.id.button_setting);
        fragmentManager = getSupportFragmentManager();
        settingFragmentIsAdded = false;
        musicPlayer = new MediaPlayer();
        fragmentManager.beginTransaction().add(R.id.fragment_container, upcomingAlarmFragment).commit();
    }
    public void loadPendingAlarmFragment(View view){
        fragmentManager.beginTransaction().remove(settingFragment).commit();
        settingFragmentIsAdded = false;
    }
    public void loadSettingFragment(View view){
        if(!settingFragmentIsAdded){
            fragmentManager.beginTransaction().add(R.id.fragment_container, settingFragment).commit();
            settingFragmentIsAdded = true;
        }
    }
    public void testPlayingMusic(View view){
        //PermissionInquirer.askStoragePermission(this);
        musicPlayer.release();
        musicPlayer = MediaPlayer.create(this, R.raw.boss_battle_a);
        musicPlayer.setLooping(true);
        musicPlayer.start();
    }
    public void stopMusic(View view){
        musicPlayer.release();
    }
    public void addAlarmAfter1Minute(View v){
        // TODO: debug purpose
        final int NOTIFICATION_ID = 1;
        final String NOTIFICATION_CHANNEL_ID = "com.example.appbaothuc";
        final String NOTIFICATION_CHANNEL_NAME = "App báo thức";
        int minute = 1;
        Calendar time = Calendar.getInstance();
        time.add(Calendar.MINUTE, minute);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent alarmServiceIntent = new Intent(this.getApplicationContext(), AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, alarmServiceIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Set alarm after " + String.valueOf(minute) + ".", Toast.LENGTH_LONG).show();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(notificationChannel);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            final Notification notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.ic_add) // TODO
                    .setContentTitle("Báo thức vào lúc:") // TODO
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            Service service = new Service(){
                @Override
                public IBinder onBind(Intent intent) {
                    return null;
                }
            };
            service.startForeground(NOTIFICATION_ID, notification);
//            startForeground(new Intent(this, new Service() {
//                @Override
//                public IBinder onBind(Intent intent) {
//                    return null;
//                }
//
//                @Override
//                public int onStartCommand(Intent intent, int flags, int startId) {
//                    startForeground(NOTIFICATION_ID, notification);
//                    return super.onStartCommand(intent, flags, startId);
//                }
//            }.getClass()));
        }
        else {
            final Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.ic_add)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("[Next Alarm]")
                    .setContentIntent(pendingIntent)
                    .build();
            Service service = new Service(){
                @Override
                public IBinder onBind(Intent intent) {
                    return null;
                }
            };
            service.startForeground(NOTIFICATION_ID, notification);
        }
    }
    public void addAlarmAfter2Minute(View v){
        // TODO: debug purpose
        final int NOTIFICATION_ID = 1;
        final String NOTIFICATION_CHANNEL_ID = "com.example.appbaothuc";
        final String NOTIFICATION_CHANNEL_NAME = "App báo thức";
        int minute = 2;
        Calendar time = Calendar.getInstance();
        time.add(Calendar.MINUTE, minute);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent alarmServiceIntent = new Intent(this.getApplicationContext(), AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, alarmServiceIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Set alarm after " + String.valueOf(minute) + ".", Toast.LENGTH_LONG).show();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(notificationChannel);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            final Notification notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.ic_add) // TODO
                    .setContentTitle("Báo thức vào lúc:") // TODO
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            Service service = new Service(){
                @Override
                public IBinder onBind(Intent intent) {
                    return null;
                }
            };
            service.startForeground(NOTIFICATION_ID, notification);
        }
        else {
            final Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.ic_add)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("[Next Alarm]")
                    .setContentIntent(pendingIntent)
                    .build();
            Service service = new Service(){
                @Override
                public IBinder onBind(Intent intent) {
                    return null;
                }
            };
            service.startForeground(NOTIFICATION_ID, notification);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int a = 1;
        int b = a;
        return true;
//        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onPause() {
        super.onPause();
//        ActivityManager activityManager = (ActivityManager) getApplicationContext()
//                .getSystemService(Context.ACTIVITY_SERVICE);
//
//        activityManager.moveTaskToFront(getTaskId(), 0);
//        Intent intent = new Intent(this,MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT |Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }
}