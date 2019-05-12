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
import com.example.appbaothuc.listeners.ChallengeActivityListener;
import com.example.appbaothuc.listeners.OnSaveChallengeStateListener;
import com.example.appbaothuc.models.MathDetail;

import java.util.ArrayList;
import java.util.Random;

import static com.example.appbaothuc.models.MathDetail.MathDifficulty.EASY;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.HARD;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.INFERNAL;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.INSANE;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.MODERATE;
import static com.example.appbaothuc.models.MathDetail.MathDifficulty.NIGHTMARE;

public class MathChallengeFragment extends Fragment implements OnSaveChallengeStateListener {
    private MathDetail mathDetail;
    private int mathDifficulty;
    private int numberOfProblem;
    private int numberOfDoneProblem;
    private ArrayList<String> listProblem;
    private ArrayList<Integer> listResult;
    private Random random;

    private TextView textViewProblemLeft;
    private TextView textViewQuestion;
    private EditText editTextResult;
    private ImageButton buttonConfirm;

    private ChallengeActivityListener challengeActivityListener;

    public void setMathDetail(MathDetail mathDetail) {
        this.mathDetail = mathDetail;
        this.mathDifficulty = mathDetail.getDifficulty();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.challengeActivityListener = (ChallengeActivityListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundleChallenge = getArguments();
        View view = inflater.inflate(R.layout.fragment_math_challenge, container, false);

        listProblem = new ArrayList<>();
        listResult = new ArrayList<>();
        random = new Random();

        textViewProblemLeft = view.findViewById(R.id.text_view_problem_left);
        textViewQuestion = view.findViewById(R.id.text_view_question);
        editTextResult = view.findViewById(R.id.edit_text_result);
        buttonConfirm = view.findViewById(R.id.button_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userResult = Integer.parseInt(editTextResult.getText().toString());
                if(userResult == listResult.get(numberOfDoneProblem)){ // A tricky use of numberOfDoneProblem
                    numberOfDoneProblem++;
                    if(numberOfDoneProblem == numberOfProblem){
                        buttonConfirm.setEnabled(false);
                        challengeActivityListener.onFinishChallenge();
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
            this.numberOfProblem = bundleChallenge.getInt("numberOfProblem");
            this.numberOfDoneProblem = bundleChallenge.getInt("numberOfDoneProblem");
            this.listProblem = bundleChallenge.getStringArrayList("listProblem");
            this.listResult = bundleChallenge.getIntegerArrayList("listResult");
            this.editTextResult.setText(bundleChallenge.getString("editTextResultString"));
            this.textViewQuestion.setText(listProblem.get(numberOfDoneProblem));
        }
        else{
            this.numberOfProblem = this.mathDetail.getNumberOfProblem();
            this.numberOfDoneProblem = 0;
            generateCalculation();
        }
        this.textViewProblemLeft.setText("Left: " + (numberOfProblem - numberOfDoneProblem));
        return view;
    }

    private void getNextCalculation(){
        this.textViewProblemLeft.setText("Left: " + (numberOfProblem - numberOfDoneProblem));
        this.textViewQuestion.setText(listProblem.get(numberOfDoneProblem));
        this.editTextResult.setText("");
    }
    private void generateCalculation(){
        int maxRange = 0;
        switch (mathDifficulty){
            case EASY:
                maxRange = 10;
                break;
            case MODERATE:
                maxRange = 20;
                break;
            case HARD:
                maxRange = 50;
                break;
            case INSANE:
                maxRange = 100;
                break;
            case NIGHTMARE:
                maxRange = 1000;
                break;
            case INFERNAL:
                maxRange = 10000;
                break;
        }
        for(int i = 0; i < numberOfProblem; i++){
            int a = random.nextInt(maxRange);
            int b = random.nextInt(maxRange);
            int c = random.nextInt(maxRange);
            String Problem = "(" + a + " x " + b + ") + " + c;
            int result = a * b + c;
            listProblem.add(Problem);
            listResult.add(result);
        }
        getNextCalculation();
    }
    public static String getCalculationExample(int mathDifficulty){
        Random random = new Random();
        int maxRange = 0;
        switch (mathDifficulty){
            case EASY:
                maxRange = 10;
                break;
            case MODERATE:
                maxRange = 20;
                break;
            case HARD:
                maxRange = 50;
                break;
            case INSANE:
                maxRange = 100;
                break;
            case NIGHTMARE:
                maxRange = 1000;
                break;
            case INFERNAL:
                maxRange = 10000;
                break;
        }
        int a = random.nextInt(maxRange);
        int b = random.nextInt(maxRange);
        int c = random.nextInt(maxRange);
        return "(" + a + " x " + b + ") + " + c;
    }

    @Override
    public Bundle onSaveChallengeState() {
        Bundle mathSavedState = new Bundle();
        mathSavedState.putInt("numberOfProblem", numberOfProblem);
        mathSavedState.putInt("numberOfDoneProblem", numberOfDoneProblem);
        mathSavedState.putStringArrayList("listProblem", listProblem);
        mathSavedState.putIntegerArrayList("listResult", listResult);
        mathSavedState.putString("editTextResultString", editTextResult.getText().toString());
        return mathSavedState;
    }
}