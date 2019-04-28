package com.example.appbaothuc.challenge;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbaothuc.R;
import com.example.appbaothuc.interfaces.ChallengeActivityListener;
import com.peanut.androidlib.sensormanager.ShakeDetector;

public class ShakeChallengeFragment extends Fragment implements ChallengeActivityListener, ShakeDetector.ShakeListener {
    private Context context;
    private TextView textViewCount;
    private Difficulty difficulty = Difficulty.Easy; // TODO: hard-coded
    private int countDownFrom = 20; // TODO: hard-coded
    private long minInterval;
    private float minForce;
    private ShakeDetector shakeDetector;
    private ChallengeActivityListener hostDialogListener;
    private ChallengeActivityListener shakeDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.hostDialogListener = (ChallengeActivityListener) context;
        this.shakeDialogListener = (ChallengeActivityListener) getParentFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundleChallenge = getArguments();
        View view = inflater.inflate(R.layout.fragment_shake_challenge, container, false);
        textViewCount = view.findViewById(R.id.text_view_count);
        textViewCount.setText(String.valueOf(countDownFrom));
        shakeDetector = ShakeDetector.newInstance(context);
        minInterval = 200;
        switch(difficulty){
            case Easy:
                minForce = 50;
                break;
            case Moderate:
                minForce = 75;
                break;
            case Hard:
                minForce = 100;
                break;
        }
        shakeDetector.configure(minInterval, minForce);
        shakeDetector.start(this);

        if(bundleChallenge != null){
            countDownFrom = bundleChallenge.getInt("countDownFrom");
            minInterval = bundleChallenge.getLong("minInterval");
            minForce = bundleChallenge.getFloat("minForce");
            textViewCount.setText(String.valueOf(countDownFrom));
        }
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.shakeDetector.stop();
    }



    @Override
    public Bundle onGetSavedState() {
        Bundle shakeSavedState = new Bundle();
        shakeSavedState.putInt("countDownFrom", countDownFrom);
        shakeSavedState.putLong("minInterval", minInterval);
        shakeSavedState.putFloat("minForce", minForce);
        return shakeSavedState;
    }

    @Override
    public void onAccelerationChange(float v, float v1, float v2) {

    }

    @Override
    public void onShake(float v) {
        Toast.makeText(context, "Shake detection.", Toast.LENGTH_SHORT).show();
        countDownFrom--;
        if(countDownFrom == 0){
            hostDialogListener.onFinishChallenge();
            shakeDialogListener.onFinishChallenge();
        }
        else{
            textViewCount.setText(String.valueOf(countDownFrom));
        }
    }

    @Override
    public void onSupportDetection() {

    }

    @Override
    public void onNoSupportDetection() {

    }

    @Override
    public void onStopDetection() {
        Toast.makeText(context, "Shake detection has stopped.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishChallenge() {

    }

    enum Difficulty{
        Easy, Moderate, Hard
    }
}