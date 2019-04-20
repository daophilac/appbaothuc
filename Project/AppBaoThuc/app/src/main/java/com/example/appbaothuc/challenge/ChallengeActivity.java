package com.example.appbaothuc.challenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.appbaothuc.Alarm;
import com.example.appbaothuc.R;

public class ChallengeActivity extends AppCompatActivity {
    enum ChallengeType{
        Default, Math, Shake
    }
    private Alarm alarm;
    public static final int defaultRingtoneId = R.raw.in_the_busting_square; // TODO
    public static final String defaultRingtoneName = "In the busting square"; // TODO
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        byte[] byteAlarm = getIntent().getExtras().getByteArray("alarm");
        alarm = Alarm.toParcelable(byteAlarm, Alarm.CREATOR);
        // TODO: Debug purpose
        alarm.setLabel("Do exercise");
        // TODO
        FragmentManager fragmentManager = getSupportFragmentManager();
        ChallengeDialogFragment challengeDialogFragment = ChallengeDialogFragment.newInstance(alarm,"");
        challengeDialogFragment.show(fragmentManager, this.getLocalClassName());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    public interface OnFinishChallengeListener{
        void onFinishChallenge();
    }
}