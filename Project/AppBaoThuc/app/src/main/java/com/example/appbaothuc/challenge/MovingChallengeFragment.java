package com.example.appbaothuc.challenge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appbaothuc.R;
import com.example.appbaothuc.models.MovingDetail;
import com.peanut.androidlib.sensormanager.LocationTracker;
import com.peanut.androidlib.sensormanager.MovingDetector;

import static android.app.Activity.RESULT_OK;

public class MovingChallengeFragment extends Fragment {
    private ChallengeActivity challengeActivity;
    private ChallengeDialogFragment challengeDialogFragment;
    private MovingDetail movingDetail;
    private float distance;
    private TextView textViewDistance;
    private MovingDetector.LocationDetector locationDetector;

    public void setMovingDetail(MovingDetail movingDetail) {
        this.movingDetail = movingDetail;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        challengeActivity = (ChallengeActivity) context;
        challengeDialogFragment = (ChallengeDialogFragment) getParentFragment();
        challengeDialogFragment.setOnSaveChallengeState(new ChallengeDialogFragment.OnSaveChallengeState() {
            @Override
            public Bundle onSaveChallengeState() {
                Bundle movingSavedState = new Bundle();
                movingSavedState.putFloat("distance", distance);
                return movingSavedState;
            }
        });
        locationDetector = MovingDetector.newInstance(context, new LocationTracker.LocationServiceListener() {
            @Override
            public void onLocationServiceOff() {
                locationDetector.requestSelfLocationSettings(1);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                context.sendBroadcast(intent);
                locationDetector.pause();
            }

            @Override
            public void onLocationServiceOn() {
                locationDetector.resume();
            }
        });
        locationDetector.checkLocationSetting(new LocationTracker.OnLocationSettingResultListener() {
            @Override
            public void onSatisfiedSetting() {
                start();
            }

            @Override
            public void onUnsatisfiedSetting(Exception e) {
                locationDetector.requestSelfLocationSettings(1);
            }
        });
    }
    private void start(){
        locationDetector.start(new MovingDetector.MovingDetectorListener() {
            @Override
            public void onMoved(float distance1, String furtherDetails) {
                distance -= distance1;
                if(distance <= 0){
                    challengeActivity.challengeFinished();
                }
                else{
                    textViewDistance.setText(String.format("%.3f", distance));
                }
            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundleChallenge = getArguments();
        View view = inflater.inflate(R.layout.fragment_challenge_moving, container, false);
        textViewDistance = view.findViewById(R.id.text_view_distance);

        if(bundleChallenge != null){
            distance = bundleChallenge.getFloat("distance");
            textViewDistance.setText(String.format("%.3f", distance));
        }
        else{
            distance = movingDetail.getDistance();
            textViewDistance.setText(String.format("%.3f", distance));
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationDetector.stop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    start();
                }
                else{
                    locationDetector.requestSelfLocationSettings(1);
                }
                break;
        }
    }
}
