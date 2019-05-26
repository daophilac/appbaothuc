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
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.R;
import com.example.appbaothuc.challenge.MathChallengeFragment;
import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.models.MathDetail;
import com.example.appbaothuc.models.MovingDetail;

import static com.example.appbaothuc.models.ChallengeType.MOVING;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.INFERNAL;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.INSANE;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.NIGHTMARE;

public class AlternativeMathConfigurationFragment extends Fragment {
    private MovingConfigurationFragment movingConfigurationFragment;
    private DatabaseHandler databaseHandler;
    private Context context;
    private Alarm alarm;
    private MovingDetail movingDetail;
    private MathDetail mathDetail;
    private TextView textViewMathNumberOfProblem;
    private NumberPicker numberPickerNumberOfProblem;
    private TextView textViewCalculationExample;
    private RadioGroup radioGroup;
    private RadioButton radioButtonInsane;
    private RadioButton radioButtonNightmare;
    private RadioButton radioButtonInfernal;
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
        this.context = context;
        if(alarm.getChallengeType() != MOVING){
            mathDetail = MathDetail.obtainAlternative(movingDetail.getIdAlarm());
        }
        else {
            mathDetail = databaseHandler.getAlarmMathDetail(movingDetail.getIdAlarm());
            if(mathDetail == null){
                mathDetail = MathDetail.obtainAlternative(movingDetail.getIdAlarm());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        alarm = null;
        movingDetail = null;
        radioGroup.clearCheck();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        this.textViewMathNumberOfProblem.setText(this.mathDetail.getNumberOfProblem() + " problem(s)");
        this.numberPickerNumberOfProblem.setMinValue(10);
        this.numberPickerNumberOfProblem.setMaxValue(999999999);
        this.numberPickerNumberOfProblem.setWrapSelectorWheel(false);
        this.numberPickerNumberOfProblem.setValue(this.mathDetail.getNumberOfProblem());

        switch(this.mathDetail.getDifficulty()){
            case INSANE:
                this.radioButtonInsane.performClick();
                break;
            case NIGHTMARE:
                this.radioButtonNightmare.performClick();
                break;
            case INFERNAL:
                this.radioButtonInfernal.performClick();
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuration_alternative_math, container, false);
        this.textViewMathNumberOfProblem = view.findViewById(R.id.text_view_math_number_of_problem);
        this.numberPickerNumberOfProblem = view.findViewById(R.id.number_picker_math_number_of_problem);
        this.textViewCalculationExample = view.findViewById(R.id.text_view_calculation_example);
        this.radioGroup = view.findViewById(R.id.radio_group_math_difficulty);
        this.radioButtonInsane = view.findViewById(R.id.radio_button_insane);
        this.radioButtonNightmare = view.findViewById(R.id.radio_button_nightmare);
        this.radioButtonInfernal = view.findViewById(R.id.radio_button_infernal);
        this.buttonCancel = view.findViewById(R.id.button_cancel);
        this.buttonOk = view.findViewById(R.id.button_ok);

        this.numberPickerNumberOfProblem.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mathDetail.setNumberOfProblem(newVal);
                textViewMathNumberOfProblem.setText(newVal + " problem(s)");
            }
        });
        this.radioButtonInsane.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mathDetail.setDifficulty(INSANE);
                    textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(INSANE));
                }
            }
        });
        this.radioButtonNightmare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mathDetail.setDifficulty(NIGHTMARE);
                    textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(NIGHTMARE));
                }
            }
        });
        this.radioButtonInfernal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mathDetail.setDifficulty(INFERNAL);
                    textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(INFERNAL));
                }
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
                movingDetail.setMathDetail(mathDetail);
                movingConfigurationFragment.getAlternativeMath(mathDetail);
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }
}
