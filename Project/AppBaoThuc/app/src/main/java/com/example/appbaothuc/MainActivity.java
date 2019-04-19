package com.example.appbaothuc;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.appbaothuc.services.NotificationService;

public class MainActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "APPBAOTHUC.db";
    private DatabaseHandler databaseHandler;
    private ImageButton buttonAlarm;
    private ImageButton buttonSetting;
    private UpcomingAlarmFragment upcomingAlarmFragment;
    private SettingFragment settingFragment;
    private FragmentManager fragmentManager;
    private MediaPlayer musicPlayer;

    private boolean settingFragmentIsAdded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionInquirer permissionInquirer = new PermissionInquirer(this);
        permissionInquirer.askPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
        databaseHandler = new DatabaseHandler(this, DATABASE_NAME, null, 1);
        buttonAlarm = findViewById(R.id.button_alarm);
        buttonSetting = findViewById(R.id.button_setting);
        buttonAlarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadUpcomingAlarmFragment(v);
            }
        });
        buttonSetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadSettingFragment(v);
            }
        });
        settingFragment = SettingFragment.newInstance(); // TODO: should use the default constructor instead.
        settingFragment.setEnterTransition(new Slide(Gravity.END));
        settingFragment.setExitTransition(new Slide(Gravity.END));
        upcomingAlarmFragment = new UpcomingAlarmFragment();
        fragmentManager = getSupportFragmentManager();
        settingFragmentIsAdded = false;
        musicPlayer = new MediaPlayer();
        fragmentManager.beginTransaction().add(R.id.main_fragment_container, upcomingAlarmFragment).commit();
        MainActivity.restartAlarmService(this);
    }
    public void loadUpcomingAlarmFragment(View view){
        fragmentManager.beginTransaction().remove(settingFragment).commit();
        settingFragmentIsAdded = false;
    }
    public void loadSettingFragment(View view){
        if(!settingFragmentIsAdded){
            fragmentManager.beginTransaction().add(R.id.main_fragment_container, settingFragment).commit();
            settingFragmentIsAdded = true;
        }
    }
    public void test1(View view){
        FragmentTest fragmentTest = new FragmentTest();
        fragmentManager.beginTransaction().add(R.id.main_fragment_container, fragmentTest).commit();
    }
    public void test2(View view){

    }
    public static void restartAlarmService(Context context){
        Intent notificationIntent = new Intent(context, NotificationService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(notificationIntent);
            context.startService(notificationIntent);
        }
        else{
            context.startService(notificationIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Read external storage permission has not been granted. Terminate!", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }
}