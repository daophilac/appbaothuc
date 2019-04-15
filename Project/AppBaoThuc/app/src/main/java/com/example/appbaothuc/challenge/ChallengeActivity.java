package com.example.appbaothuc.challenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.appbaothuc.Alarm;
import com.example.appbaothuc.R;

public class ChallengeActivity extends AppCompatActivity {
    private Alarm alarm;
    public static final int defaultRingtoneId = R.raw.boss_battle_a; //TODO
    public static final String defaultRingtoneName = "Boss battle A";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarm = getIntent().getExtras().getParcelable("alarm");
        // TODO: Debug purpose
        String ringtoneUrl = "/sdcard/music/7.1. Final Frontier (feat. Merethe Soltvedt).flac";
        alarm.setRingtoneUrl(ringtoneUrl);
        alarm.setRingtoneName(ringtoneUrl.substring(ringtoneUrl.lastIndexOf("/") + 1, ringtoneUrl.lastIndexOf(".")));
        alarm.setLabel("Do exercise");
        // TODO
        FragmentManager fragmentManager = getSupportFragmentManager();
        ChallengeDialogFragment challengeDialogFragment = ChallengeDialogFragment.newInstance(alarm,"");
        challengeDialogFragment.show(fragmentManager, "challenge_dialog_fragment");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
}