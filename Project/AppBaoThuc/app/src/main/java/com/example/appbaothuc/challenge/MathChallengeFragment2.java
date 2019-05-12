package com.example.appbaothuc.challenge;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class MathChallengeFragment2 extends Fragment implements OnSaveChallengeStateListener {
    private MathDetail mathDetail;
    private int mathDifficulty;
    private int iNumCaculate;
    private int iNumberOfDoneCalculation;
    private Random random;
    private ArrayList<String> listCaculate;
    private ArrayList<Integer> listResult;
    private ArrayList arr;

    private TextView textView_Question;
    private EditText editText_Result;
    private Button buttonConfirm;
    private ImageButton buttonDelete;
    private String sUserResult="";

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
        View view = inflater.inflate(R.layout.fragment_math_challenge2, container, false);
        textView_Question=view.findViewById(R.id.textView_Question);
        editText_Result=view.findViewById(R.id.editText_Result);
        editText_Result.setSelection(editText_Result.getText().length());
        buttonConfirm=view.findViewById(R.id.btnConfirm);
        buttonDelete=view.findViewById(R.id.btnDelete);
        listCaculate=new ArrayList<>();
        listResult=new ArrayList<>();
//        generateCalculation();

        final Button btnSo0=view.findViewById(R.id.so0);
        btnSo0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo0.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo1=view.findViewById(R.id.so1);
        btnSo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo1.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo2=view.findViewById(R.id.so2);
        btnSo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo2.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo3=view.findViewById(R.id.so3);
        btnSo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo3.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo4=view.findViewById(R.id.so4);
        btnSo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo4.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo5=view.findViewById(R.id.so5);
        btnSo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo5.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo6=view.findViewById(R.id.so6);
        btnSo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo6.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo7=view.findViewById(R.id.so7);
        btnSo7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo7.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo8=view.findViewById(R.id.so8);
        btnSo8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo8.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo9=view.findViewById(R.id.so9);
        btnSo9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo9.getText();
                editText_Result.setText(sUserResult);
            }
        });
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int iUserResult=Integer.parseInt(editText_Result.getText().toString());
                if(iUserResult==listResult.get(iNumberOfDoneCalculation)){
                    iNumberOfDoneCalculation++;
                    if(iNumberOfDoneCalculation==iNumCaculate){
                        challengeActivityListener.onFinishChallenge();
//                        mathDialogListener.onFinishChallenge();
//                        buttonConfirm.setEnabled(false);
//                        textView_Question.setText("Done!");
//                        textView_Question.setTextColor(Color.GREEN);
//                        editText_Result.setText("");
                    }
                    else{
                        getNextCaculate();
                    }
                }
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sSysResult=sUserResult;
                sUserResult=sSysResult.substring(0,sUserResult.length()-1);

                editText_Result.setText(sUserResult);
            }
        });




        if(bundleChallenge != null){
            this.iNumCaculate = bundleChallenge.getInt("iNumCaculate");
            this.iNumberOfDoneCalculation = bundleChallenge.getInt("iNumberOfDoneCalculation");
            this.listCaculate = bundleChallenge.getStringArrayList("listCaculate");
            this.listResult = bundleChallenge.getIntegerArrayList("listResult");
            this.editText_Result.setText(bundleChallenge.getString("editText_ResultString"));
            this.textView_Question.setText(listCaculate.get(iNumberOfDoneCalculation));
        }
        else{
            this.iNumCaculate = this.mathDetail.getNumberOfProblem();
            this.iNumberOfDoneCalculation = 0;
            generateCalculation();
        }
        return view;
    }
    private void getNextCaculate(){
        sUserResult="";
        textView_Question.setText(listCaculate.get(iNumberOfDoneCalculation));
        editText_Result.setText("");

    }
    private void generateCalculation(){
        random = new Random();
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

        for(int i=0;i<iNumCaculate;i++) {
            int a = random.nextInt(maxRange);
            int b = random.nextInt(maxRange);
            int c = random.nextInt(maxRange);

            String sCaculation= "(" + a + " x " + b + ") + "+ c;
            int iResult=a*b+c;
            listCaculate.add(sCaculation);
            listResult.add(iResult);
        }
        getNextCaculate();
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
        mathSavedState.putInt("iNumCaculate", iNumCaculate);
        mathSavedState.putInt("iNumberOfDoneCalculation", iNumberOfDoneCalculation);
        mathSavedState.putStringArrayList("listCaculate", listCaculate);
        mathSavedState.putIntegerArrayList("listResult", listResult);
        mathSavedState.putString("editText_ResultString", editText_Result.getText().toString());
        return mathSavedState;
    }
}