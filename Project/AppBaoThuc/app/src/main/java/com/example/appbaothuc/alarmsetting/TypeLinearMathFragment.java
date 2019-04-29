package com.example.appbaothuc.alarmsetting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.appbaothuc.R;

public class TypeLinearMathFragment extends Fragment {

    private TextView textViewProblems, textViewMath;

    private SeekBar seekBarDifficult;
    private Button buttonLinearMathCancel, buttonLinearMathOk;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_type_linear_math, container, false);
        setControl(view);
        return view;
    }
    public void setControl(View view){
        textViewProblems = view.findViewById(R.id.textViewProblems);
        textViewMath = view.findViewById(R.id.textViewMath);
        seekBarDifficult = view.findViewById(R.id.seekBarDifficult);
        buttonLinearMathCancel = view.findViewById(R.id.buttonLinearMathCancel);
        buttonLinearMathOk = view.findViewById(R.id.buttonLinearMathOk);

        buttonLinearMathCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(TypeLinearMathFragment.this).commit();
            }
        });
        buttonLinearMathOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypeFragment.challengeType = 1;
                getFragmentManager().beginTransaction().remove(TypeLinearMathFragment.this).commit();
            }
        });
    }

}
