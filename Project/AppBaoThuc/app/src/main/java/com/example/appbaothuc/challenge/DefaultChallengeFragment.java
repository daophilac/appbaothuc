package com.example.appbaothuc.challenge;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appbaothuc.R;
import com.example.appbaothuc.listeners.ChallengeActivityListener;
import com.example.appbaothuc.listeners.OnSaveChallengeStateListener;

public class DefaultChallengeFragment extends Fragment{
    private Button buttonDismiss;
    private ChallengeActivityListener challengeActivityListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.challengeActivityListener = (ChallengeActivityListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_challenge, container, false);
        buttonDismiss = view.findViewById(R.id.button_dismiss);
        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challengeActivityListener.onFinishChallenge();
            }
        });
        return view;
    }
}
