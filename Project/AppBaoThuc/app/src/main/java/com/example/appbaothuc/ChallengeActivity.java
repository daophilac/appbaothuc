package com.example.appbaothuc;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;

import java.security.Key;

public class ChallengeActivity extends AppCompatActivity {
    private KeyPressListener keyPressListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        FragmentManager fragmentManager = getSupportFragmentManager();
        ChallengeDialogFragment challengeDialogFragment = ChallengeDialogFragment.newInstance("");
        //keyPressListener = challengeDialogFragment;
        //challengeDialogFragment.show(fragmentManager, "challenge_dialog_fragment");
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
    public interface KeyPressListener{
        void onKeyDown(int keyCode, KeyEvent keyEvent);
    }
}
