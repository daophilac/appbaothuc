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
        FragmentManager fragmentManager = getSupportFragmentManager();
        ChallengeDialogFragment challengeDialogFragment = ChallengeDialogFragment.newInstance("");
        challengeDialogFragment.show(fragmentManager, "challenge_dialog_fragment");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
    public interface KeyPressListener{
        void onKeyDown(int keyCode, KeyEvent keyEvent);
    }
}