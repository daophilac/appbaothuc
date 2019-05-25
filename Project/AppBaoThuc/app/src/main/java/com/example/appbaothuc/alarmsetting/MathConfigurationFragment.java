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
import android.widget.TextView;

import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.R;
import com.example.appbaothuc.challenge.MathChallengeFragment;
import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.models.MathDetail;

import java.util.ArrayList;
import java.util.List;

import static com.example.appbaothuc.models.MathDetail.MathDifficulty.EASY;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.HARD;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.INFERNAL;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.INSANE;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.MODERATE;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.NIGHTMARE;

public class MathConfigurationFragment extends Fragment {
    private Context context;
    private MathConfigurationFragmentListener listener;
    private Alarm alarm;
    private MathDetail mathDetail;
    private DatabaseHandler databaseHandler;
    private TextView textViewMathNumberOfProblem;
    private NumberPicker numberPickerNumberOfProblem;
    private TextView textViewCalculationExample;
    private RadioButton radioButtonEasy;
    private RadioButton radioButtonModerate;
    private RadioButton radioButtonHard;
    private RadioButton radioButtonInsane;
    private RadioButton radioButtonNightmare;
    private RadioButton radioButtonInfernal;
    private Button buttonCancel;
    private Button buttonOk;
    private List<RadioButton> listRadioButton;

    public void configure(TypeFragment typeFragment, Alarm alarm){
        this.listener = typeFragment;
        this.alarm = alarm;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.databaseHandler = new DatabaseHandler(this.context);
        this.mathDetail = this.databaseHandler.getAlarmMathDetail(this.alarm.getIdAlarm());
        if(this.mathDetail == null){
            this.mathDetail = MathDetail.obtain(this.alarm.getIdAlarm());
        }
        this.listRadioButton = new ArrayList<>();
    }

    @Override
    public void onStop() {
        super.onStop();
        for(RadioButton rd : this.listRadioButton){
            if(rd.isChecked()){
                rd.setChecked(false);
                return;
            }
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        initializeDefaultValue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_math_configuration2, container, false);
        this.textViewMathNumberOfProblem = view.findViewById(R.id.text_view_math_number_of_problem);
        this.numberPickerNumberOfProblem = view.findViewById(R.id.number_picker_math_number_of_problem);
        this.textViewCalculationExample = view.findViewById(R.id.text_view_calculation_example);
        this.radioButtonEasy = view.findViewById(R.id.radio_button_easy);
        this.radioButtonModerate = view.findViewById(R.id.radio_button_moderate);
        this.radioButtonHard = view.findViewById(R.id.radio_button_hard);
        this.radioButtonInsane = view.findViewById(R.id.radio_button_insane);
        this.radioButtonNightmare = view.findViewById(R.id.radio_button_nightmare);
        this.radioButtonInfernal = view.findViewById(R.id.radio_button_infernal);
        this.buttonCancel = view.findViewById(R.id.button_give_up);
        this.buttonOk = view.findViewById(R.id.button_ok);

        this.numberPickerNumberOfProblem.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mathDetail.setNumberOfProblem(newVal);
                textViewMathNumberOfProblem.setText(newVal + " problem(s)");
            }
        });
        this.radioButtonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonClick(v);
                mathDetail.setDifficulty(EASY);
                textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(EASY));
            }
        });
        this.radioButtonModerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonClick(v);
                mathDetail.setDifficulty(MODERATE);
                textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(MODERATE));
            }
        });
        this.radioButtonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonClick(v);
                mathDetail.setDifficulty(HARD);
                textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(HARD));
            }
        });
        this.radioButtonInsane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonClick(v);
                mathDetail.setDifficulty(INSANE);
                textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(INSANE));
            }
        });
        this.radioButtonNightmare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonClick(v);
                mathDetail.setDifficulty(NIGHTMARE);
                textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(NIGHTMARE));
            }
        });
        this.radioButtonInfernal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonClick(v);
                mathDetail.setDifficulty(INFERNAL);
                textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(INFERNAL));
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
                listener.onMathConfigurationSetup(mathDetail);
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private void initializeDefaultValue(){
        this.listRadioButton.add(radioButtonEasy);
        this.listRadioButton.add(radioButtonModerate);
        this.listRadioButton.add(radioButtonHard);
        this.listRadioButton.add(radioButtonInsane);
        this.listRadioButton.add(radioButtonNightmare);
        this.listRadioButton.add(radioButtonInfernal);

        this.textViewMathNumberOfProblem.setText(this.mathDetail.getNumberOfProblem() + " problem(s)");
        this.numberPickerNumberOfProblem.setMinValue(1);
        this.numberPickerNumberOfProblem.setMaxValue(999999999);
        this.numberPickerNumberOfProblem.setWrapSelectorWheel(false);
        this.numberPickerNumberOfProblem.setValue(this.mathDetail.getNumberOfProblem());

        switch(this.mathDetail.getDifficulty()){
            case EASY:
                this.radioButtonEasy.performClick();
                break;
            case MODERATE:
                this.radioButtonModerate.performClick();
                break;
            case HARD:
                this.radioButtonHard.performClick();
                break;
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

    private void radioButtonClick(View v){
        for(RadioButton rd : this.listRadioButton){
            if(rd.isChecked() && rd != v){
                rd.setChecked(false);
                return;
            }
        }
    }

    public interface MathConfigurationFragmentListener{
        void onMathConfigurationSetup(MathDetail mathDetail);
    }
}