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

import com.example.appbaothuc.MainActivity;
import com.example.appbaothuc.R;
import com.example.appbaothuc.challenge.MathChallengeFragment;
import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.models.MathDetail;

public class TypeLinearMathFragment extends Fragment {
//    private Context context;
//    private TypeLinearMathFragmentListener listener;
//    private Alarm alarm;
//    private MathDetail mathDetail;
//    private DatabaseHandler databaseHandler;
//    private TextView textViewMathNumberOfProblem;
//    private NumberPicker numberPickerNumberOfProblem;
//    private TextView textViewCalculationExample;
//    private RadioButton radioButtonEasy;
//    private RadioButton radioButtonModerate;
//    private RadioButton radioButtonHard;
//    private RadioButton radioButtonInsane;
//    private RadioButton radioButtonNightmare;
//    private RadioButton radioButtonInfernal;
//    private Button buttonCancel;
//    private Button buttonOk;
//
//    private ConfigurationMode configurationMode;
//    private RadioButton radioButtonPreviousChecked;
//    private RadioButton radioButtonNowChecked;
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        this.context = context;
//        this.databaseHandler = new DatabaseHandler(this.context);
//        this.mathDetail = this.databaseHandler.getAlarmMathDetail(this.alarm.getIdAlarm());
//        if(this.mathDetail == null){
//            this.configurationMode = ConfigurationMode.ADD_NEW;
//            this.mathDetail = MathDetail.obtain(this.alarm.getIdAlarm());
//        }
//        else{
//            this.configurationMode = ConfigurationMode.EDIT;
//        }
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_math_configuration, container, false);
//        this.textViewMathNumberOfProblem = view.findViewById(R.id.text_view_math_number_of_problem);
//        this.numberPickerNumberOfProblem = view.findViewById(R.id.number_picker_math_number_of_problem);
//        this.textViewCalculationExample = view.findViewById(R.id.text_view_calculation_example);
//        this.radioButtonEasy = view.findViewById(R.id.radio_button_easy);
//        this.radioButtonModerate = view.findViewById(R.id.radio_button_moderate);
//        this.radioButtonHard = view.findViewById(R.id.radio_button_hard);
//        this.radioButtonInsane = view.findViewById(R.id.radio_button_insane);
//        this.radioButtonNightmare = view.findViewById(R.id.radio_button_nightmare);
//        this.radioButtonInfernal = view.findViewById(R.id.radio_button_infernal);
//        this.buttonCancel = view.findViewById(R.id.button_cancel);
//        this.buttonOk = view.findViewById(R.id.button_ok);
//
//        this.textViewMathNumberOfProblem.setText(String.format(MainActivity.locale, getString(R.string.math_number_of_problem), this.mathDetail.getNumberOfProblem()));
//        this.numberPickerNumberOfProblem.setMinValue(1);
//        this.numberPickerNumberOfProblem.setMaxValue(999999999);
//        this.numberPickerNumberOfProblem.setWrapSelectorWheel(false);
//        this.numberPickerNumberOfProblem.setValue(this.mathDetail.getNumberOfProblem());
//
//        this.numberPickerNumberOfProblem.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                mathDetail.setNumberOfProblem(newVal);
//                textViewMathNumberOfProblem.setText(String.format(MainActivity.locale, getString(R.string.math_number_of_problem), newVal));
//            }
//        });
//        this.radioButtonEasy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                radioButtonClick(v);
//                mathDetail.setDifficulty(MathDetail.MathDifficulty.EASY);
//                textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(MathDetail.MathDifficulty.EASY));
//            }
//        });
//        this.radioButtonModerate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                radioButtonClick(v);
//                mathDetail.setDifficulty(MathDetail.MathDifficulty.MODERATE);
//                textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(MathDetail.MathDifficulty.MODERATE));
//            }
//        });
//        this.radioButtonHard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                radioButtonClick(v);
//                mathDetail.setDifficulty(MathDetail.MathDifficulty.HARD);
//                textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(MathDetail.MathDifficulty.HARD));
//            }
//        });
//        this.radioButtonInsane.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                radioButtonClick(v);
//                mathDetail.setDifficulty(MathDetail.MathDifficulty.INSANE);
//                textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(MathDetail.MathDifficulty.INSANE));
//            }
//        });
//        this.radioButtonNightmare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                radioButtonClick(v);
//                mathDetail.setDifficulty(MathDetail.MathDifficulty.NIGHTMARE);
//                textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(MathDetail.MathDifficulty.NIGHTMARE));
//            }
//        });
//        this.radioButtonInfernal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                radioButtonClick(v);
//                mathDetail.setDifficulty(MathDetail.MathDifficulty.INFERNAL);
//                textViewCalculationExample.setText(MathChallengeFragment.getCalculationExample(MathDetail.MathDifficulty.INFERNAL));
//            }
//        });
//        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFragmentManager().beginTransaction().remove(MathConfigurationFragment.this).commit();
//            }
//        });
//        this.buttonOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onMathConfigurationSetup(mathDetail);
//                getFragmentManager().beginTransaction().remove(MathConfigurationFragment.this).commit();
//            }
//        });
//
//        switch(this.mathDetail.getDifficulty()){
//            case 1:
//                this.radioButtonPreviousChecked = this.radioButtonEasy;
//                this.radioButtonEasy.performClick();
//                break;
//            case 2:
//                this.radioButtonPreviousChecked = this.radioButtonModerate;
//                this.radioButtonModerate.performClick();
//                break;
//            case 3:
//                this.radioButtonPreviousChecked = this.radioButtonHard;
//                this.radioButtonHard.performClick();
//                break;
//            case 4:
//                this.radioButtonPreviousChecked = this.radioButtonInsane;
//                this.radioButtonInsane.performClick();
//                break;
//            case 5:
//                this.radioButtonPreviousChecked = this.radioButtonNightmare;
//                this.radioButtonNightmare.performClick();
//                break;
//            case 6:
//                this.radioButtonPreviousChecked = this.radioButtonInfernal;
//                this.radioButtonInfernal.performClick();
//                break;
//        }
//        return view;
//    }
//
//    private void radioButtonClick(View v){
//        this.radioButtonNowChecked = (RadioButton)v;
//        if(this.radioButtonNowChecked != this.radioButtonPreviousChecked){
//            this.radioButtonPreviousChecked.setChecked(false);
//        }
//        else{
//            this.radioButtonPreviousChecked.setChecked(true);
//        }
//        this.radioButtonPreviousChecked = this.radioButtonNowChecked;
//    }
//
//    public interface TypeLinearMathFragmentListener{
//        void onMathSetup(MathDetail mathDetail);
//    }
//    private enum ConfigurationMode{
//        ADD_NEW, EDIT
//    }
}
