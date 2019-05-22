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
import com.example.appbaothuc.services.MusicPlayerService;
import com.example.appbaothuc.services.NotificationService;
import com.peanut.androidlib.activitymanager.ActivityFromDeath;

import java.util.HashMap;

import static com.example.appbaothuc.appsetting.AppSettingFragment.autoDismissAfter;
import static com.example.appbaothuc.services.MusicPlayerService.AlarmMusicPlayerCommand.START;
import static com.example.appbaothuc.services.MusicPlayerService.AlarmMusicPlayerCommand.STOP;

public class ChallengeActivity extends AppCompatActivity {
    private Alarm alarm;
    private ChallengeDialogFragment challengeDialogFragment;
    private static Thread threadTimeout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        ActivityFromDeath.sendOnCreateSignal(this);
        ActivityFromDeath.start(new ActivityFromDeath.ActivityFromDeathListener() {
            @Override
            public void onTimeToInitialize() {
                byte[] byteAlarm = getIntent().getExtras().getByteArray("byteAlarm");
                alarm = Alarm.toParcelable(byteAlarm, Alarm.CREATOR);
                FragmentManager fragmentManager = getSupportFragmentManager();
                challengeDialogFragment = ChallengeDialogFragment.newInstance(alarm,"");
                challengeDialogFragment.show(fragmentManager, getLocalClassName());
            }

            @Override
            public HashMap<String, Bundle> onTimeToBuildBundle() {
                Bundle bundleActivity = new Bundle();
                byte[] byteAlarm = Alarm.toByteArray(alarm);
                bundleActivity.putByteArray("byteAlarm", byteAlarm);
                Bundle bundleChallenge = challengeDialogFragment.buildBundleForRecreation();
                HashMap<String, Bundle> result = new HashMap<>();
                result.put("bundleActivity", bundleActivity);
                result.put("bundleChallenge", bundleChallenge);
                return result;
            }

            @Override
            public void onTimeToGetBundle(HashMap<String, Bundle> mapBundle) {
                Bundle bundleActivity = mapBundle.get("bundleActivity");
                byte[] byteAlarm = bundleActivity.getByteArray("byteAlarm");
                alarm = Alarm.toParcelable(byteAlarm, Alarm.CREATOR);
                Bundle bundleChallenge = mapBundle.get("bundleChallenge");
                FragmentManager fragmentManager = getSupportFragmentManager();
                challengeDialogFragment = ChallengeDialogFragment.newInstance(alarm,"");
                challengeDialogFragment.setArguments(bundleChallenge);
                challengeDialogFragment.show(fragmentManager, getLocalClassName());

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        ActivityFromDeath.sendOnResumeSignal(this);
        if(!MusicPlayerService.isPlaying()){
            byte[] byteAlarm = Alarm.toByteArray(alarm);
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
        if(threadTimeout == null){
            threadTimeout = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(autoDismissAfter * 60000);
                        if(MusicPlayerService.isPlaying()){
                            challengeFinished();
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
        ActivityFromDeath.sendOnStopSignal(this);
    }
    public void challengeFinished(){
        ActivityFromDeath.stop();
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
        NotificationService.update(this);
    }
}