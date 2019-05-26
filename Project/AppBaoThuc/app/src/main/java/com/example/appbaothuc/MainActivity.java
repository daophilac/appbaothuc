package com.example.appbaothuc;

import android.Manifest;
import android.app.Notification;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.appbaothuc.appsetting.AppSettingFragment;
import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.services.NotificationService;
import com.peanut.androidlib.permissionmanager.PermissionInquirer;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    private ImageButton buttonAlarm;
    private ImageButton buttonSetting;
    public UpcomingAlarmFragment upcomingAlarmFragment;
    public AppSettingFragment appSettingFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public boolean appSettingFragmentIsAdded;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionInquirer permissionInquirer = new PermissionInquirer(this);
        permissionInquirer.askPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);

        setControl();

        Resources resources = getResources();
        int resId = R.raw.in_the_busting_square;
        Music.defaultRingtoneUrl = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(resId) + '/' + resources.getResourceTypeName(resId) + '/' + resources.getResourceEntryName(resId);

        appSettingFragment = new AppSettingFragment();
        appSettingFragment.loadAppSetting(this);
        appSettingFragment.setEnterTransition(new Slide(Gravity.END));
        appSettingFragment.setExitTransition(new Slide(Gravity.END));

        upcomingAlarmFragment = new UpcomingAlarmFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_fragment_container, upcomingAlarmFragment).commit();
        NotificationService.update(this);

        setEvent();
    }

    private void setControl() {
        buttonAlarm = findViewById(R.id.button_alarm);
        buttonSetting = findViewById(R.id.button_setting);
    }

    private void setEvent() {
        buttonAlarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(appSettingFragmentIsAdded){
                    fragmentManager.popBackStack();
                }
            }
        });
        buttonSetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!appSettingFragmentIsAdded){
                    appSettingFragmentIsAdded = true;
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.main_fragment_container, appSettingFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    public void test1(View view){
        NotificationService.DEBUG_MODE = true;
        NotificationService.update(this);
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