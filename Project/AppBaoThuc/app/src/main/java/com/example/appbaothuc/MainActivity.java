package com.example.appbaothuc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.appbaothuc.services.AlarmService;
import com.example.appbaothuc.services.NotificationService;

import java.sql.Time;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "APPBAOTHUC";
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
        Calendar time1 = Calendar.getInstance();
        Calendar time2 = Calendar.getInstance();
        Calendar time3 = Calendar.getInstance();
        Calendar time4 = Calendar.getInstance();
        Calendar time5 = Calendar.getInstance();
        time1.add(Calendar.MINUTE, 5);
        time2.add(Calendar.MINUTE, 3);
        time3.add(Calendar.MINUTE, 1);
        time4.add(Calendar.MINUTE, 4);
        time5.add(Calendar.MINUTE, 2);
        int hour1 = time1.get(Calendar.HOUR_OF_DAY);
        int hour2 = time2.get(Calendar.HOUR_OF_DAY);
        int hour3 = time3.get(Calendar.HOUR_OF_DAY);
        int hour4 = time4.get(Calendar.HOUR_OF_DAY);
        int hour5 = time5.get(Calendar.HOUR_OF_DAY);
        int minute1 = time1.get(Calendar.MINUTE);
        int minute2 = time2.get(Calendar.MINUTE);
        int minute3 = time3.get(Calendar.MINUTE);
        int minute4 = time4.get(Calendar.MINUTE);
        int minute5 = time5.get(Calendar.MINUTE);
        databaseHandler.insertAlarm("alarm 1", hour1, minute1);
        databaseHandler.insertAlarm("alarm 2", hour2, minute2);
        databaseHandler.insertAlarm("alarm 3", hour3, minute3);
        databaseHandler.insertAlarm("alarm 4", hour4, minute4);
        databaseHandler.insertAlarm("alarm 5", hour5, minute5);
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
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
}