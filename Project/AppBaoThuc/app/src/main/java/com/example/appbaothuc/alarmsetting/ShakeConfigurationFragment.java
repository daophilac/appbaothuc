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
import com.example.appbaothuc.models.ShakeDetail;

import static com.example.appbaothuc.models.ShakeDetail.ShakeDifficulty.EASY;
import static com.example.appbaothuc.models.ShakeDetail.ShakeDifficulty.HARD;
import static com.example.appbaothuc.models.ShakeDetail.ShakeDifficulty.MODERATE;

public class ShakeConfigurationFragment extends Fragment {
    private Context context;
    private ShakeConfigurationFragmentListener listener;
    private Alarm alarm;
    private ShakeDetail shakeDetail;
    private DatabaseHandler databaseHandler;
    private TextView textViewShakeNumberOfProblem;
    private NumberPicker numberPickerNumberOfProblem;
    private TextView textViewPlus25;
    private TextView textViewMinus25;
    private TextView textViewPlus50;
    private TextView textViewMinus50;
    private RadioGroup radioGroup;
    private RadioButton radioButtonEasy;
    private RadioButton radioButtonModerate;
    private RadioButton radioButtonHard;
    private Button buttonCancel;
    private Button buttonOk;

    public void configure(TypeFragment typeFragment, Alarm alarm){
        this.listener = typeFragment;
        this.alarm = alarm;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.databaseHandler = new DatabaseHandler(this.context);
        this.shakeDetail = this.databaseHandler.getAlarmShakeDetail(this.alarm.getIdAlarm());
        if(this.shakeDetail == null){
            this.shakeDetail = ShakeDetail.obtain(this.alarm.getIdAlarm());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        this.radioGroup.clearCheck();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.databaseHandler = null;
        this.context = null;
        this.alarm = null;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        initializeDefaultValue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shake_configuration, container, false);
        this.textViewShakeNumberOfProblem = view.findViewById(R.id.text_view_shake_number_of_problem);
        this.numberPickerNumberOfProblem = view.findViewById(R.id.number_picker_shake_number_of_problem);
        this.textViewPlus25 = view.findViewById(R.id.text_view_plus_25);
        this.textViewMinus25 = view.findViewById(R.id.text_view_minus_25);
        this.textViewPlus50 = view.findViewById(R.id.text_view_plus_50);
        this.textViewMinus50 = view.findViewById(R.id.text_view_minus_50);
        this.radioGroup = view.findViewById(R.id.radio_group_shake_difficulty);
        this.radioButtonEasy = view.findViewById(R.id.radio_button_easy);
        this.radioButtonModerate = view.findViewById(R.id.radio_button_moderate);
        this.radioButtonHard = view.findViewById(R.id.radio_button_hard);
        this.buttonCancel = view.findViewById(R.id.button_give_up);
        this.buttonOk = view.findViewById(R.id.button_ok);

        this.numberPickerNumberOfProblem.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                shakeDetail.setNumberOfProblem(newVal);
                textViewShakeNumberOfProblem.setText("Shake for " + newVal + " times");
            }
        });
        this.textViewPlus25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newValue = shakeDetail.getNumberOfProblem() + 25;
                shakeDetail.setNumberOfProblem(newValue);
                numberPickerNumberOfProblem.setValue(newValue);
                textViewShakeNumberOfProblem.setText("Shake for " + newValue + " times");
            }
        });
        this.textViewMinus25.setOnClickListener(new View.OnClickListener() {
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
        this.textViewPlus50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newValue = shakeDetail.getNumberOfProblem() + 50;
                shakeDetail.setNumberOfProblem(newValue);
                numberPickerNumberOfProblem.setValue(newValue);
                textViewShakeNumberOfProblem.setText("Shake for " + newValue + " times");
            }
        });
        this.textViewMinus50.setOnClickListener(new View.OnClickListener() {
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
        this.radioButtonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shakeDetail.setDifficulty(EASY);
            }
        });
        this.radioButtonModerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shakeDetail.setDifficulty(MODERATE);
            }
        });
        this.radioButtonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shakeDetail.setDifficulty(HARD);
            }
        });
        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        this.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onShakeConfigurationSetup(shakeDetail);
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private void initializeDefaultValue(){
        textViewShakeNumberOfProblem.setText("Shake for " + this.shakeDetail.getNumberOfProblem() + " times");
        this.numberPickerNumberOfProblem.setMinValue(50);
        this.numberPickerNumberOfProblem.setMaxValue(1000000000);
        this.numberPickerNumberOfProblem.setWrapSelectorWheel(false);
        this.numberPickerNumberOfProblem.setValue(this.shakeDetail.getNumberOfProblem());

        switch(this.shakeDetail.getDifficulty()){
            case EASY:
                this.radioButtonEasy.setChecked(true);
                break;
            case MODERATE:
                this.radioButtonModerate.setChecked(true);
                break;
            case HARD:
                this.radioButtonHard.setChecked(true);
                break;
        }
    }

    public interface ShakeConfigurationFragmentListener{
        void onShakeConfigurationSetup(ShakeDetail shakeDetail);
    }
}