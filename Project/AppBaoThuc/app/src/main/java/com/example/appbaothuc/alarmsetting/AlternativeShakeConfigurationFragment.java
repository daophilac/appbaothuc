package com.example.appbaothuc.alarmsetting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.R;
import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.models.MovingDetail;
import com.example.appbaothuc.models.ShakeDetail;

import static com.example.appbaothuc.models.ChallengeType.MOVING;
import static com.example.appbaothuc.models.ShakeDetail.ShakeDifficulty.HARD;
import static com.example.appbaothuc.models.ShakeDetail.ShakeDifficulty.MODERATE;

public class AlternativeShakeConfigurationFragment extends Fragment {
    private MovingConfigurationFragment movingConfigurationFragment;
    private DatabaseHandler databaseHandler;
    private Alarm alarm;
    private MovingDetail movingDetail;
    private ShakeDetail shakeDetail;
    private TextView textViewShakeNumberOfProblem;
    private NumberPicker numberPickerNumberOfProblem;
    private TextView textViewPlus25;
    private TextView textViewMinus25;
    private TextView textViewPlus50;
    private TextView textViewMinus50;
    private RadioGroup radioGroup;
    private RadioButton radioButtonModerate;
    private RadioButton radioButtonHard;
    private Button buttonCancel;
    private Button buttonOk;
    public void configure(MovingConfigurationFragment movingConfigurationFragment, Alarm alarm, MovingDetail movingDetail){
        this.movingConfigurationFragment = movingConfigurationFragment;
        this.alarm = alarm;
        this.movingDetail = movingDetail;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.databaseHandler = new DatabaseHandler(context);
        if(alarm.getChallengeType() != MOVING){
            shakeDetail = ShakeDetail.obtainAlternative(movingDetail.getIdAlarm());
        }
        else {
            shakeDetail = databaseHandler.getAlarmShakeDetail(movingDetail.getIdAlarm());
            if(shakeDetail == null){
                shakeDetail = ShakeDetail.obtainAlternative(movingDetail.getIdAlarm());
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuration_alternative_shake, container, false);
        textViewShakeNumberOfProblem = view.findViewById(R.id.text_view_shake_number_of_problem);
        numberPickerNumberOfProblem = view.findViewById(R.id.number_picker_shake_number_of_problem);
        textViewPlus25 = view.findViewById(R.id.text_view_plus_25);
        textViewMinus25 = view.findViewById(R.id.text_view_minus_25);
        textViewPlus50 = view.findViewById(R.id.text_view_plus_50);
        textViewMinus50 = view.findViewById(R.id.text_view_minus_50);
        radioGroup = view.findViewById(R.id.radio_group_shake_difficulty);
        radioButtonModerate = view.findViewById(R.id.radio_button_moderate);
        radioButtonHard = view.findViewById(R.id.radio_button_hard);
        buttonOk = view.findViewById(R.id.button_ok);
        buttonCancel = view.findViewById(R.id.button_cancel);

        numberPickerNumberOfProblem.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                shakeDetail.setNumberOfProblem(newVal);
                textViewShakeNumberOfProblem.setText("Shake for " + newVal + " times");
            }
        });
        textViewPlus25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newValue = shakeDetail.getNumberOfProblem() + 25;
                shakeDetail.setNumberOfProblem(newValue);
                numberPickerNumberOfProblem.setValue(newValue);
                textViewShakeNumberOfProblem.setText("Shake for " + newValue + " times");
            }
        });
        textViewMinus25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shakeDetail.getNumberOfProblem() <= 50){
                    return;
                }
                int newValue = shakeDetail.getNumberOfProblem() - 25;
                shakeDetail.setNumberOfProblem(newValue);
                numberPickerNumberOfProblem.setValue(newValue);
                textViewShakeNumberOfProblem.setText("Shake for " + newValue + " times");
            }
        });
        textViewPlus50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newValue = shakeDetail.getNumberOfProblem() + 50;
                shakeDetail.setNumberOfProblem(newValue);
                numberPickerNumberOfProblem.setValue(newValue);
                textViewShakeNumberOfProblem.setText("Shake for " + newValue + " times");
            }
        });
        textViewMinus50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shakeDetail.getNumberOfProblem() <= 50){
                    return;
                }
                int newValue = shakeDetail.getNumberOfProblem() - 50;
                shakeDetail.setNumberOfProblem(newValue);
                numberPickerNumberOfProblem.setValue(newValue);
                textViewShakeNumberOfProblem.setText("Shake for " + newValue + " times");
            }
        });
        radioButtonModerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shakeDetail.setDifficulty(MODERATE);
            }
        });
        radioButtonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shakeDetail.setDifficulty(HARD);
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movingDetail.setShakeDetail(shakeDetail);
                movingConfigurationFragment.getAlternativeShake(shakeDetail);
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        textViewShakeNumberOfProblem.setText("Shake for " + shakeDetail.getNumberOfProblem() + " times");
        numberPickerNumberOfProblem.setWrapSelectorWheel(false);
        numberPickerNumberOfProblem.setMinValue(200);
        numberPickerNumberOfProblem.setMaxValue(1000000000);
        numberPickerNumberOfProblem.setValue(shakeDetail.getNumberOfProblem());
        switch(shakeDetail.getDifficulty()){
            case MODERATE:
                radioButtonModerate.setChecked(true);
                break;
            case HARD:
                radioButtonHard.setChecked(true);
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        alarm = null;
        movingDetail = null;
        radioGroup.clearCheck();
    }
}