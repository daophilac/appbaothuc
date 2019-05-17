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
import com.example.appbaothuc.models.ShakeDetail;
import com.peanut.androidlib.sensormanager.ShakeDetector;

import static com.example.appbaothuc.models.ShakeDetail.ShakeDifficulty.EASY;
import static com.example.appbaothuc.models.ShakeDetail.ShakeDifficulty.HARD;
import static com.example.appbaothuc.models.ShakeDetail.ShakeDifficulty.MODERATE;

public class ShakeChallengeFragment extends Fragment {
    private ChallengeActivity challengeActivity;
    private ChallengeDialogFragment challengeDialogFragment;
    private ShakeDetail shakeDetail;
    private int shakeDifficulty;
    private int numberOfProblem;
    private long minInterval;
    private float minForce;
    private TextView textViewShakeNumberOfProblem;
    private ShakeDetector shakeDetector;

    public void setShakeDetail(ShakeDetail shakeDetail) {
        this.shakeDetail = shakeDetail;
        this.shakeDifficulty = shakeDetail.getDifficulty();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        challengeActivity = (ChallengeActivity) context;
        challengeDialogFragment = (ChallengeDialogFragment) getParentFragment();
        challengeDialogFragment.setOnSaveChallengeState(new ChallengeDialogFragment.OnSaveChallengeState() {
            @Override
            public Bundle onSaveChallengeState() {
                Bundle shakeSavedState = new Bundle();
                shakeSavedState.putInt("numberOfProblem", numberOfProblem);
                shakeSavedState.putLong("minInterval", minInterval);
                shakeSavedState.putFloat("minForce", minForce);
                return shakeSavedState;
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundleChallenge = getArguments();
        View view = inflater.inflate(R.layout.fragment_shake_challenge, container, false);
        this.textViewShakeNumberOfProblem = view.findViewById(R.id.text_view_shake_number_of_problem);
        this.textViewShakeNumberOfProblem.setText(String.valueOf(this.numberOfProblem));
        shakeDetector = new ShakeDetector(challengeActivity);
        this.minInterval = 100;
        switch(shakeDifficulty){
            case EASY:
                this.minForce = 20;
                break;
            case MODERATE:
                this.minForce = 40;
                break;
            case HARD:
                this.minForce = 60;
                break;
        }
        this.shakeDetector.configure(this.minInterval, this.minForce);
        this.shakeDetector.start(new ShakeDetector.ShakeDetectorListener() {
            @Override
            public void onAccelerationChange(float x, float y, float z) {

            }

            @Override
            public void onShake(float force) {
                numberOfProblem--;
                if(numberOfProblem == 0){
                    challengeActivity.challengeFinished();
                }
                else{
                    textViewShakeNumberOfProblem.setText("Shake for " + numberOfProblem + " times");
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

            }
        });

        if(bundleChallenge != null){
            this.numberOfProblem = bundleChallenge.getInt("numberOfProblem");
            this.minInterval = bundleChallenge.getLong("minInterval");
            this.minForce = bundleChallenge.getFloat("minForce");
            this.textViewShakeNumberOfProblem.setText("Shake for " + this.numberOfProblem + " times");
        }
        else{
            this.numberOfProblem = this.shakeDetail.getNumberOfProblem();
            this.textViewShakeNumberOfProblem.setText("Shake for " + this.numberOfProblem + " times");
        }
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.shakeDetector.stop();
    }
}