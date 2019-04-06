package com.example.appbaothuc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
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
        PermissionInquirer.askStoragePermission(this);
        musicPlayer.release();
        musicPlayer = MediaPlayer.create(this, R.raw.kodoku_na_junrei);
        musicPlayer.setLooping(true);
        musicPlayer.start();
    }
    public void stopMusic(View view){
        musicPlayer.release();
    }
}