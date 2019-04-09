package com.example.appbaothuc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class ChallengeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ChallengeDialogFragment challengeDialogFragment = ChallengeDialogFragment.newInstance("");
        challengeDialogFragment.show(fragmentManager, "challenge_dialog_fragment");
    }
}
