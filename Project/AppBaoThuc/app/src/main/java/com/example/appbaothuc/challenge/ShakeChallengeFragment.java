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
import com.example.appbaothuc.listeners.ChallengeActivityListener;
import com.example.appbaothuc.listeners.OnSaveChallengeStateListener;
import com.example.appbaothuc.models.ShakeDetail;
import com.peanut.androidlib.sensormanager.ShakeDetector;

import static com.example.appbaothuc.models.ShakeDetail.ShakeDifficulty.EASY;
import static com.example.appbaothuc.models.ShakeDetail.ShakeDifficulty.HARD;
import static com.example.appbaothuc.models.ShakeDetail.ShakeDifficulty.MODERATE;

public class ShakeChallengeFragment extends Fragment implements OnSaveChallengeStateListener, ShakeDetector.ShakeListener {
    private Context context;
    private ShakeDetail shakeDetail;
    private int shakeDifficulty;
    private int numberOfProblem;
    private long minInterval;
    private float minForce;
    private TextView textViewShakeNumberOfProblem;
    private ShakeDetector shakeDetector;
    private ChallengeActivityListener challengeActivityListener;

    public void setShakeDetail(ShakeDetail shakeDetail) {
        this.shakeDetail = shakeDetail;
        this.shakeDifficulty = shakeDetail.getDifficulty();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.challengeActivityListener = (ChallengeActivityListener) context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundleChallenge = getArguments();
        View view = inflater.inflate(R.layout.fragment_shake_challenge, container, false);
        this.textViewShakeNumberOfProblem = view.findViewById(R.id.text_view_shake_number_of_problem);
        this.textViewShakeNumberOfProblem.setText(String.valueOf(this.numberOfProblem));
        this.shakeDetector = ShakeDetector.newInstance(this.context);
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
        this.shakeDetector.start(this);

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

    @Override
    public Bundle onSaveChallengeState() {
        Bundle shakeSavedState = new Bundle();
        shakeSavedState.putInt("numberOfProblem", this.numberOfProblem);
        shakeSavedState.putLong("minInterval", this.minInterval);
        shakeSavedState.putFloat("minForce", this.minForce);
        return shakeSavedState;
    }

    @Override
    public void onAccelerationChange(float v, float v1, float v2) {

    }

    @Override
    public void onShake(float v) {
        Toast.makeText(this.context, "Shake detection.", Toast.LENGTH_SHORT).show();
        this.numberOfProblem--;
        if(this.numberOfProblem == 0){
            this.challengeActivityListener.onFinishChallenge();
        }
        else{
            this.textViewShakeNumberOfProblem.setText("Shake for " + this.numberOfProblem + " times");
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
//        Toast.makeText(context, "Shake detection has stopped.", Toast.LENGTH_SHORT).show();
    }
}