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
import com.peanut.library.sensormanager.ShakeDetector;
import com.peanut.library.sensormanager.ShakeListener;

public class ShakeChallengeFragment extends Fragment implements ShakeListener{
    private Context context;
    private TextView textViewCount;
    private int countDownFrom = 20; // TODO: hard-code
    private ShakeDetector shakeDetector;
    private ChallengeActivity.OnFinishChallengeListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shake_challenge, container, false);
        textViewCount = view.findViewById(R.id.text_view_count);
        textViewCount.setText(String.valueOf(countDownFrom));
        shakeDetector = ShakeDetector.newInstance(context);
        shakeDetector.configure(200, 50);
        shakeDetector.start(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.listener = (ChallengeActivity.OnFinishChallengeListener) getParentFragment();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.shakeDetector.stop();
    }

    @Override
    public void onAccelerationChange(float v, float v1, float v2) {

    }

    @Override
    public void onShake(float v) {
        Toast.makeText(context, "Shake detection.", Toast.LENGTH_SHORT).show();
        countDownFrom--;
        if(countDownFrom == 0){
            listener.onFinishChallenge();
            return;
        }
        textViewCount.setText(String.valueOf(countDownFrom));
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
}
