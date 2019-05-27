package com.example.appbaothuc.alarmsetting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.R;
import com.example.appbaothuc.challenge.MovingChallengeFragment;
import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.models.ChallengeType;
import com.example.appbaothuc.models.MathDetail;
import com.example.appbaothuc.models.MovingDetail;
import com.example.appbaothuc.models.ShakeDetail;
import com.peanut.androidlib.view.DistancePicker;
import com.peanut.androidlib.view.MeasurementPicker;

import static com.example.appbaothuc.models.ChallengeType.MATH;
import static com.example.appbaothuc.models.ChallengeType.MOVING;
import static com.example.appbaothuc.models.ChallengeType.SHAKE;

public class MovingConfigurationFragment extends Fragment {
    private OnMovingConfigurationListener onMovingConfigurationListener;
    private DatabaseHandler databaseHandler;
    private Context context;
    private Alarm alarm;
    private MovingDetail movingDetail;

    private DistancePicker distancePicker;
    private LinearLayout linearLayoutAddMoreDistance;
    private ImageButton imageButtonInfo;
    private ConstraintLayout constraintLayoutAlternativeShakeChallenge;
    private ConstraintLayout constraintLayoutAlternativeMathChallenge;
    private Button buttonCancel;
    private Button buttonOk;

    private FragmentManager fragmentManager;
    private AlternativeShakeConfigurationFragment alternativeShakeConfigurationFragment;
    private AlternativeMathConfigurationFragment alternativeMathConfigurationFragment;
    public void setAlarm(Alarm alarm){
        this.alarm = alarm;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        databaseHandler = new DatabaseHandler(context);
        movingDetail = databaseHandler.getAlarmMovingDetail(alarm.getIdAlarm());
        if(movingDetail == null){
            movingDetail = MovingDetail.obtain(alarm.getIdAlarm());
        }
        fragmentManager = getFragmentManager();
        alternativeShakeConfigurationFragment = new AlternativeShakeConfigurationFragment();
        alternativeMathConfigurationFragment = new AlternativeMathConfigurationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuration_moving, container, false);
        linearLayoutAddMoreDistance = view.findViewById(R.id.linear_layout_add_more_distance);
        distancePicker = view.findViewById(R.id.distance_picker);
        imageButtonInfo = view.findViewById(R.id.image_button_info);
        constraintLayoutAlternativeShakeChallenge = view.findViewById(R.id.constraint_layout_alternative_shake_challenge);
        constraintLayoutAlternativeMathChallenge = view.findViewById(R.id.constraint_layout_alternative_math_challenge);
        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonOk = view.findViewById(R.id.button_ok);
        distancePicker.updateWrapSelectorWheel(false);
        distancePicker.setMeasurements(new MeasurementPicker.Measurement[]{MeasurementPicker.Measurement.METER, MeasurementPicker.Measurement.KILOMETER});
        distancePicker.setMeasurement(movingDetail.getMeasurement());
        switch (movingDetail.getAlternativeChallenge()){
            case MATH:
                constraintLayoutAlternativeMathChallenge.setBackgroundColor(getResources().getColor(R.color.challenge_layout_activate));
                break;
            case SHAKE:
                constraintLayoutAlternativeShakeChallenge.setBackgroundColor(getResources().getColor(R.color.challenge_layout_activate));
                break;
        }
        switch (movingDetail.getMeasurement()){
            case METER:
                linearLayoutAddMoreDistance.setVisibility(View.VISIBLE);
                distancePicker.setBaseMinValue(2).setBaseMaxValue(1000).setMultiplicationFactor(50).perform();
                distancePicker.setSelectedValue(movingDetail.getDistance());
                break;
            case KILOMETER:
                linearLayoutAddMoreDistance.setVisibility(View.INVISIBLE);
                distancePicker.setBaseMinValue(1).setBaseMaxValue(1000).setMultiplicationFactor(1).perform();
                distancePicker.setSelectedValue(movingDetail.getDistance());
                break;
        }
        distancePicker.setOnMeasurementChangeListener(new MeasurementPicker.OnMeasurementChangeListener() {
            @Override
            public void onMeasurementChange(MeasurementPicker measurementPicker, MeasurementPicker.Measurement oldValue, MeasurementPicker.Measurement newValue) {
                movingDetail.setMeasurement(newValue);
                if(newValue == MeasurementPicker.Measurement.KILOMETER){
                    linearLayoutAddMoreDistance.setVisibility(View.INVISIBLE);
                    distancePicker.setBaseMinValue(1).setBaseMaxValue(1000).setMultiplicationFactor(1).perform();
                    distancePicker.setSelectedValue(1);
                }
                else{
                    linearLayoutAddMoreDistance.setVisibility(View.VISIBLE);
                    distancePicker.setBaseMinValue(2).setBaseMaxValue(1000).setMultiplicationFactor(50).perform();
                    distancePicker.setSelectedValue(100);
                }
            }
        });
        distancePicker.setOnValueChangeListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                movingDetail.setDistance(newVal);
            }
        });
        imageButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlternativeChallengeDialogFragment().show(fragmentManager, null);
            }
        });
        constraintLayoutAlternativeMathChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alternativeMathConfigurationFragment.configure(MovingConfigurationFragment.this, alarm, movingDetail);
                fragmentManager.beginTransaction()
                        .add(R.id.full_screen_fragment_container, alternativeMathConfigurationFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        constraintLayoutAlternativeShakeChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alternativeShakeConfigurationFragment.configure(MovingConfigurationFragment.this, alarm, movingDetail);
                fragmentManager.beginTransaction()
                        .add(R.id.full_screen_fragment_container, alternativeShakeConfigurationFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMovingConfigurationListener.onConfirm(movingDetail);
                fragmentManager.popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        alarm = null;
    }

    private void updateAlternativeChallengeLayoutColor(ChallengeType newChallenge){
        switch (movingDetail.getAlternativeChallenge()){
            case MATH:
                constraintLayoutAlternativeMathChallenge.setBackgroundColor(getResources().getColor(R.color.challenge_layout_deactivate));
                break;
            case SHAKE:
                constraintLayoutAlternativeShakeChallenge.setBackgroundColor(getResources().getColor(R.color.challenge_layout_deactivate));
                break;
        }
        switch (newChallenge){
            case MATH:
                constraintLayoutAlternativeMathChallenge.setBackgroundColor(getResources().getColor(R.color.challenge_layout_activate));
                break;
            case SHAKE:
                constraintLayoutAlternativeShakeChallenge.setBackgroundColor(getResources().getColor(R.color.challenge_layout_activate));
                break;
        }
        movingDetail.setAlternativeChallenge(newChallenge);
    }

    public void getAlternativeMath(MathDetail mathDetail){
        updateAlternativeChallengeLayoutColor(MATH);
        if(alarm.getChallengeType() == MATH){
            if(movingDetail.getAlternativeChallenge() == MATH){
                databaseHandler.updateMathDetail(mathDetail);
            }
            else{
                switch (movingDetail.getAlternativeChallenge()){
                    case MATH:
                        break;
                    case SHAKE:
                        databaseHandler.deleteShakeDetail(movingDetail.getIdAlarm());
                        break;
                }
            }
        }
    }
    public void getAlternativeShake(ShakeDetail shakeDetail){
        updateAlternativeChallengeLayoutColor(ChallengeType.SHAKE);
        if(alarm.getChallengeType() == MOVING){
            if(movingDetail.getAlternativeChallenge() == SHAKE){
                databaseHandler.updateShakeDetail(shakeDetail);
            }
            else{
                switch (movingDetail.getAlternativeChallenge()){
                    case MATH:
                        databaseHandler.deleteMathDetail(movingDetail.getIdAlarm());
                        break;
                    case SHAKE:
                        break;
                }
            }
        }
    }

    public void setOnMovingConfigurationListener(OnMovingConfigurationListener onMovingConfigurationListener) {
        this.onMovingConfigurationListener = onMovingConfigurationListener;
    }

    public interface OnMovingConfigurationListener{
        void onConfirm(MovingDetail movingDetail);
    }
}
