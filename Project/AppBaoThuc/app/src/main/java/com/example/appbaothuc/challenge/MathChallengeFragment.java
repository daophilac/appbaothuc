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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MathChallengeFragment extends Fragment {
    enum Difficulty{
        Easy, Moderate, Hard, Insane, Nightmare, Infernal
    }
    private Difficulty difficulty = Difficulty.Easy; // TODO: Hard-coded
    private int numberOfCalculation = 2; // TODO: Hard-coded
    private int numberOfDoneCalculation = 0;
    private List<String> listCalculation;
    private List<Integer> listResult;
    private Random random;


    private TextView textViewQuestion;
    private EditText editTextResult;
    private ImageButton buttonConfirm;

    private ChallengeActivity.OnFinishChallengeListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                        listener.onFinishChallenge();
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
        generateCalculation();
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ChallengeActivity.OnFinishChallengeListener) getParentFragment();
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
            String calculation = String.valueOf(a) + " x " + String.valueOf(b) + " + " + String.valueOf(c);
            int result = a * b + c;
            listCalculation.add(calculation);
            listResult.add(result);
        }
        getNextCalculation();
    }
}
