package com.moon.baothuc;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int iNumCaculate=2;
    private int iNumberOfDoneCalculation = 0;
    private Random random;
    private List<String> listCaculate;
    private  List<Integer> listResult;
    private  ArrayList arr;

    private TextView textView_Question;
    private EditText editText_Result;
    private Button buttonConfirm;
    private ImageButton buttonDelete;
    private String sUserResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_Question=(TextView)findViewById(R.id.textView_Question);
        editText_Result=(EditText)findViewById(R.id.editText_Result);
        editText_Result.setSelection(editText_Result.getText().length());
        buttonConfirm=(Button)findViewById(R.id.btnConfirm);
        buttonDelete=(ImageButton)findViewById(R.id.btnDelete);
        listCaculate=new ArrayList<>();
        listResult=new ArrayList<>();
        generateCalculation();

        final Button btnSo0=(Button)findViewById(R.id.so0);
        btnSo0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo0.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo1=(Button)findViewById(R.id.so1);
        btnSo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo1.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo2=(Button)findViewById(R.id.so2);
        btnSo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo2.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo3=(Button)findViewById(R.id.so3);
        btnSo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo3.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo4=(Button)findViewById(R.id.so4);
        btnSo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo4.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo5=(Button)findViewById(R.id.so5);
        btnSo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo5.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo6=(Button)findViewById(R.id.so6);
        btnSo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo6.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo7=(Button)findViewById(R.id.so7);
        btnSo7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo7.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo8=(Button)findViewById(R.id.so8);
        btnSo8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo8.getText();
                editText_Result.setText(sUserResult);
            }
        });
        final Button btnSo9=(Button)findViewById(R.id.so9);
        btnSo9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserResult+=btnSo9.getText();
                editText_Result.setText(sUserResult);
            }
        });


//                buttonConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int iUserResult=Integer.parseInt(editText_Result.getText().toString());
//                if(iUserResult==Integer.parseInt(sSysResult)){
//
//
//                        buttonConfirm.setEnabled(false);
//                        textView_Question.setText("Done!");
//                        textView_Question.setTextColor(Color.GREEN);
//                        editText_Result.setText("");
//
//
//                }
//                else{
//
//                    textView_Question.setText("!");
//                    textView_Question.setTextColor(Color.RED);
//                    editText_Result.setText("");
//                }
//                }
//        });
        //generateCalculation();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int iUserResult=Integer.parseInt(editText_Result.getText().toString());
                if(iUserResult==listResult.get(iNumberOfDoneCalculation)){
                    iNumberOfDoneCalculation++;
                    if(iNumberOfDoneCalculation==iNumCaculate){
                        buttonConfirm.setEnabled(false);
                        textView_Question.setText("Done!");
                        textView_Question.setTextColor(Color.GREEN);
                        editText_Result.setText("");

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
    }
    private void getNextCaculate(){
        sUserResult="";
        textView_Question.setText(listCaculate.get(iNumberOfDoneCalculation));
        editText_Result.setText("");

    }
    private void generateCalculation(){


        for(int i=0;i<iNumCaculate;i++) {
            random = new Random();
            int iMax=10;
            int a = random.nextInt(iMax);
            int b = random.nextInt(iMax);
            int c = random.nextInt(iMax);

            String sCaculation= String.valueOf(a) + " x " +String.valueOf(b)+" + "+String.valueOf(c);
            int iResult=a*b+c;

            //sSysResult=(String.valueOf(iResult));

            //textView_Question.setText(sCaculation);
            listCaculate.add(sCaculation);
            listResult.add(iResult);
        }
        getNextCaculate();
    }
}