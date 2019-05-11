package com.example.appbaothuc.challenge;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.appbaothuc.MainActivity;
import com.example.appbaothuc.R;
import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.interfaces.ChallengeActivityListener;
import com.example.appbaothuc.interfaces.ChallengeDialogListener;
import com.example.appbaothuc.services.AlarmMusicPlayer;
import com.example.appbaothuc.services.MusicPlayerService;
import com.peanut.androidlib.activitymanager.ActivityFromDeath;

import java.util.HashMap;

import static com.example.appbaothuc.appsetting.AppSettingFragment.autoDismissAfter;
import static com.example.appbaothuc.services.MusicPlayerService.AlarmMusicPlayerCommand.START;
import static com.example.appbaothuc.services.MusicPlayerService.AlarmMusicPlayerCommand.STOP;

public class ChallengeActivity extends AppCompatActivity implements ChallengeActivityListener, ChallengeDialogListener, ActivityFromDeath.ActivityFromDeathListener {
    private Alarm alarm;
    private ChallengeActivityListener challengeActivityListener;
    private ActivityFromDeath activityFromDeath;
    private static Thread threadTimeout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        this.activityFromDeath = new ActivityFromDeath(this);
        this.activityFromDeath.start();
    }
    @Override
    protected void onResume() {
        super.onResume();
        this.activityFromDeath.sendOnResumeSignal();
        if(threadTimeout == null){
            threadTimeout = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(autoDismissAfter * 60000);
                        if(MusicPlayerService.isPlaying()){
                            onFinishChallenge();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threadTimeout.start();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        activityFromDeath.sendOnStopSignal();
    }
    @Override
    public HashMap<String, Bundle> buildBundle() {
        Bundle bundleActivity = new Bundle();
        byte[] byteAlarm = Alarm.toByteArray(alarm);
        bundleActivity.putByteArray("byteAlarm", byteAlarm);
        Bundle bundleChallenge = challengeActivityListener.onGetSavedState();
        HashMap<String, Bundle> result = new HashMap<>();
        result.put("bundleActivity", bundleActivity);
        result.put("bundleChallenge", bundleChallenge);
        return result;
    }
    @Override
    public void getBundle() {
        Bundle bundleActivity = getIntent().getBundleExtra("bundleActivity");
        byte[] byteAlarm;
        if(bundleActivity != null){
            byteAlarm = bundleActivity.getByteArray("byteAlarm");
            alarm = Alarm.toParcelable(byteAlarm, Alarm.CREATOR);
            Bundle bundleChallenge = getIntent().getBundleExtra("bundleChallenge");
            FragmentManager fragmentManager = getSupportFragmentManager();
            ChallengeDialogFragment challengeDialogFragment = ChallengeDialogFragment.newInstance(alarm,"");
            challengeDialogFragment.setArguments(bundleChallenge);
            challengeDialogFragment.show(fragmentManager, this.getLocalClassName());
        }
        else{
            byteAlarm = getIntent().getExtras().getByteArray("alarm");
            alarm = Alarm.toParcelable(byteAlarm, Alarm.CREATOR);
            FragmentManager fragmentManager = getSupportFragmentManager();
            ChallengeDialogFragment challengeDialogFragment = ChallengeDialogFragment.newInstance(alarm,"");
            challengeDialogFragment.show(fragmentManager, this.getLocalClassName());
        }




        if(!MusicPlayerService.isPlaying()){
            Intent intent = new Intent(this, MusicPlayerService.class);
            intent.putExtra("command", START);
            intent.putExtra("byteAlarm", byteAlarm);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                startForegroundService(intent);
                startService(intent);
            }
            else{
                startService(intent);
            }
        }
    }
    @Override
    public void onChallengeActivated(ChallengeActivityListener challengeActivityListener) {
        this.challengeActivityListener = challengeActivityListener;
    }
    @Override
    public void onFinishChallenge() {
        this.activityFromDeath.stop();
        threadTimeout = null;
        finish();
        Intent intent = new Intent(this, MusicPlayerService.class);
        intent.putExtra("command", STOP);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(intent);
            startService(intent);
        }
        else{
            startService(intent);
        }
        MainActivity.restartAlarmService(this);
    }
    @Override
    public Bundle onGetSavedState() {
        return null;
    }





    public static final class ChallengeType{
        public static final int DEFAULT = 1;
        public static final int MATH = 2;
        public static final int SHAKE = 3;
    }
}