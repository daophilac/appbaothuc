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
import android.widget.Button;
import android.widget.TextView;

import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.R;
import com.example.appbaothuc.models.ChallengeType;
import com.example.appbaothuc.models.MathDetail;
import com.example.appbaothuc.models.MovingDetail;
import com.example.appbaothuc.models.ShakeDetail;
import com.peanut.androidlib.sensormanager.LocationTracker;
import com.peanut.androidlib.sensormanager.MovingDetector;
import com.peanut.androidlib.view.MeasurementPicker;

import static android.app.Activity.RESULT_OK;

public class MovingChallengeFragment extends Fragment {
    private ChallengeActivity challengeActivity;
    private ChallengeDialogFragment challengeDialogFragment;
    private MovingDetail movingDetail;
    private MathDetail mathDetail;
    private ShakeDetail shakeDetail;
    private ChallengeType alternativeChallenge;
    private float distance;
    private TextView textViewDistance;
    private TextView textViewDebugDetails;
    private Button buttonDebug;
    private Button buttonAlternative;
    private MovingDetector.LocationDetector locationDetector;
    private DatabaseHandler databaseHandler;

    public void setMovingDetail(MovingDetail movingDetail) {
        this.movingDetail = movingDetail;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        databaseHandler = new DatabaseHandler(context);
        this.alternativeChallenge = movingDetail.getAlternativeChallenge();
        switch (alternativeChallenge){
            case MATH:
                mathDetail = databaseHandler.getAlarmMathDetail(movingDetail.getIdAlarm());
                break;
            case SHAKE:
                shakeDetail = databaseHandler.getAlarmShakeDetail(movingDetail.getIdAlarm());
                break;
        }
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
        locationDetector.startDebug(new MovingDetector.MovingDetectorListenerDebug() {
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
            public void onUpdate(String accuracyRadius) {
                textViewDebugDetails.setText(accuracyRadius);
            }

            @Override
            public void onStop() {

            }
        });
//        locationDetector.start(new MovingDetector.MovingDetectorListener() {
//            @Override
//            public void onMoved(float distance1, String furtherDetails) {
//                distance -= distance1;
//                if(distance <= 0){
//                    challengeActivity.challengeFinished();
//                }
//                else{
//                    textViewDistance.setText(String.format("%.3f", distance));
//                }
//            }
//
//            @Override
//            public void onStop() {
//
//            }
//        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundleChallenge = getArguments();
        View view = inflater.inflate(R.layout.fragment_challenge_moving, container, false);
        textViewDistance = view.findViewById(R.id.text_view_distance);
        textViewDebugDetails = view.findViewById(R.id.text_view_debug_details);
        buttonAlternative = view.findViewById(R.id.button_alternative_challenge);
        buttonDebug = view.findViewById(R.id.button_debug);

        buttonAlternative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (alternativeChallenge){
                    case MATH:
                        MathChallengeFragment mathChallengeFragment = new MathChallengeFragment();
                        mathChallengeFragment.setMathDetail(mathDetail);
                        getFragmentManager().beginTransaction().add(R.id.challenge_fragment_container, mathChallengeFragment).addToBackStack(null).commit();
                        break;
                    case SHAKE:
                        ShakeChallengeFragment shakeChallengeFragment = new ShakeChallengeFragment();
                        shakeChallengeFragment.setShakeDetail(shakeDetail);
                        getFragmentManager().beginTransaction().add(R.id.challenge_fragment_container, shakeChallengeFragment).addToBackStack(null).commit();
                        break;
                }
            }
        });
        buttonDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovingChallengeDebugDialogFragment.newInstace(locationDetector).show(getFragmentManager(), null);
            }
        });
        if(bundleChallenge != null){
            distance = bundleChallenge.getFloat("distance");
            textViewDistance.setText(String.format("%.3f", distance));
        }
        else{
            distance = movingDetail.getDistance();
            if(movingDetail.getMeasurement() == MeasurementPicker.Measurement.KILOMETER){
                distance *= 1000;
            }
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
