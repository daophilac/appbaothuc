package com.example.appbaothuc;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;

import com.example.appbaothuc.services.NotificationService;

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
        // TODO



        buttonAlarm = findViewById(R.id.button_alarm);
        buttonSetting = findViewById(R.id.button_setting);
        settingFragment.setEnterTransition(new Slide(Gravity.END));
        settingFragment.setExitTransition(new Slide(Gravity.END));
        fragmentManager = getSupportFragmentManager();
        settingFragmentIsAdded = false;
        musicPlayer = new MediaPlayer();
        fragmentManager.beginTransaction().add(R.id.main_fragment_container, upcomingAlarmFragment).commit();
    }
    public void loadPendingAlarmFragment(View view){
        fragmentManager.beginTransaction().remove(settingFragment).commit();
        settingFragmentIsAdded = false;
    }
    public void loadSettingFragment(View view){
        if(!settingFragmentIsAdded){
            fragmentManager.beginTransaction().add(R.id.main_fragment_container, settingFragment).commit();
            settingFragmentIsAdded = true;
        }
    }
    public static void restartAlarmService(Context context){
        Intent notificationIntent = new Intent(context, NotificationService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(notificationIntent);
        }
//        else{
//            context.startService(notificationIntent);
//        }
        context.startService(notificationIntent);
    }
}