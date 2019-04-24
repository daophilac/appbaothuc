package com.example.appbaothuc.challenge;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.appbaothuc.R;
import com.example.appbaothuc.interfaces.ChallengeActivityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MathChallengeFragment extends Fragment implements ChallengeActivityListener{
    enum Difficulty{
        Easy, Moderate, Hard, Insane, Nightmare, Infernal
    }
    private Difficulty difficulty = Difficulty.Easy; // TODO: Hard-coded
    private int numberOfCalculation = 1; // TODO: Hard-coded
    private int numberOfDoneCalculation = 0;
    private ArrayList<String> listCalculation;
    private ArrayList<Integer> listResult;
    private Random random;

    private TextView textViewQuestion;
    private EditText editTextResult;
    private ImageButton buttonConfirm;

    private ChallengeActivityListener hostDialogListener;
    private ChallengeActivityListener mathDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.hostDialogListener = (ChallengeActivityListener) context;
        this.mathDialogListener = (ChallengeActivityListener) getParentFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundleChallenge = getArguments();
        View view = inflater.inflate(R.layout.fragment_math_challenge, container, false);

        listCalculation = new ArrayList<>();
        listResult = new ArrayList<>();
        random = new Random();

        textViewQuestion = view.findViewById(R.id.textView_question);
        editTextResult = view.findViewById(R.id.editText_result);
        buttonConfirm = view.findViewById(R.id.button_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userResult = Integer.parseInt(editTextResult.getText().toString());
                if(userResult == listResult.get(numberOfDoneCalculation)){ // A tricky use of numberOfDoneCalculation
                    numberOfDoneCalculation++;
                    if(numberOfDoneCalculation == numberOfCalculation){
                        buttonConfirm.setEnabled(false);
                        hostDialogListener.onFinishChallenge();
                        mathDialogListener.onFinishChallenge();
                        textViewQuestion.setTextColor(Color.GREEN);
                        textViewQuestion.setText("DONE");
                        editTextResult.setText("");
                        editTextResult.setEnabled(false);
                    }
                    else{
                        getNextCalculation();
                    }
                }
            }
        });

        if(bundleChallenge != null){
            numberOfCalculation = bundleChallenge.getInt("numberOfCalculation");
            numberOfDoneCalculation = bundleChallenge.getInt("numberOfDoneCalculation");
            listCalculation = bundleChallenge.getStringArrayList("listCalculation");
            listResult = bundleChallenge.getIntegerArrayList("listResult");
            editTextResult.setText(bundleChallenge.getString("editTextResultString"));
            textViewQuestion.setText(listCalculation.get(numberOfDoneCalculation));
        }
        else{
            generateCalculation();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getNextCalculation(){
        textViewQuestion.setText(listCalculation.get(numberOfDoneCalculation));
        editTextResult.setText("");
    }
    private void generateCalculation(){
        int maxRange = 10;
        switch (difficulty){
            case Easy:
                maxRange = 10;
                break;
            case Moderate:
                maxRange = 20;
                break;
            case Hard:
                maxRange = 50;
                break;
            case Insane:
                maxRange = 100;
                break;
            case Nightmare:
                maxRange = 1000;
                break;
            case Infernal:
                maxRange = 10000;
                break;
        }
        for(int i = 0; i < numberOfCalculation; i++){
            int a = random.nextInt(maxRange);
            int b = random.nextInt(maxRange);
            int c = random.nextInt(maxRange);
            String calculation = a + " x " + b + " + " + c;
            int result = a * b + c;
            listCalculation.add(calculation);
            listResult.add(result);
        }
        getNextCalculation();
    }

    @Override
    public Bundle onGetSavedState() {
        Bundle mathSavedState = new Bundle();
        mathSavedState.putInt("numberOfCalculation", numberOfCalculation);
        mathSavedState.putInt("numberOfDoneCalculation", numberOfDoneCalculation);
        mathSavedState.putStringArrayList("listCalculation", listCalculation);
        mathSavedState.putIntegerArrayList("listResult", listResult);
        mathSavedState.putString("editTextResultString", editTextResult.getText().toString());
        return mathSavedState;
    }
    @Override
    public void onFinishChallenge() {

    }
}