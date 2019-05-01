package com.example.appbaothuc.challenge;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.appbaothuc.Alarm;
import com.example.appbaothuc.interfaces.ChallengeActivityListener;
import com.example.appbaothuc.interfaces.ChallengeDialogListener;
import com.peanut.androidlib.activitymanager.ActivityFromDeath;

import java.util.HashMap;

public class ChallengeActivity extends AppCompatActivity implements ChallengeActivityListener, ChallengeDialogListener, ActivityFromDeath.ActivityFromDeathListener {
    private Alarm alarm;
    private ChallengeActivityListener challengeActivityListener;
    private ActivityFromDeath activityFromDeath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activityFromDeath = new ActivityFromDeath(this);
        this.activityFromDeath.start();
    }
    @Override
    protected void onResume() {
        super.onResume();
        this.activityFromDeath.sendOnResumeSignal();
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
        if(bundleActivity != null){
            byte[] bytesAlarm = bundleActivity.getByteArray("byteAlarm");
            alarm = Alarm.toParcelable(bytesAlarm, Alarm.CREATOR);
            Bundle bundleChallenge = getIntent().getBundleExtra("bundleChallenge");
            FragmentManager fragmentManager = getSupportFragmentManager();
            ChallengeDialogFragment challengeDialogFragment = ChallengeDialogFragment.newInstance(alarm,"");
            challengeDialogFragment.setArguments(bundleChallenge);
            challengeDialogFragment.show(fragmentManager, this.getLocalClassName());
        }
        else{
            byte[] byteAlarm = getIntent().getExtras().getByteArray("alarm");
            alarm = Alarm.toParcelable(byteAlarm, Alarm.CREATOR);
            // TODO: Debug purpose
            alarm.setLabel("Do exercise");
            // TODO
            FragmentManager fragmentManager = getSupportFragmentManager();
            ChallengeDialogFragment challengeDialogFragment = ChallengeDialogFragment.newInstance(alarm,"");
            challengeDialogFragment.show(fragmentManager, this.getLocalClassName());
        }
    }
    @Override
    public void onChallengeActivated(ChallengeActivityListener challengeActivityListener) {
        this.challengeActivityListener = challengeActivityListener;
    }
    @Override
    public void onFinishChallenge() {
        this.activityFromDeath.stop();
        finish();
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.release();
    }
    @Override
    public Bundle onGetSavedState() {
        return null;
    }





    public enum ChallengeType{
        DEFAULT(1), MATH(2), SHAKE(3);
        private int value;
        private ChallengeType(int value){
            this.value = value;
        }
        public int getValue(){
            return this.value;
        }
    }
}