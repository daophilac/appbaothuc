package com.example.alarmtypeapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class LableDialogFragment extends DialogFragment {
    private Button btnOk;
    private Button btnCancel;
    private EditText mEditText;


    public interface LabelDialogListener {
        void onFinishEditDialog(String inputText);
    }
    private LabelDialogListener listener;

    public LableDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_name, container);
        mEditText = view.findViewById(R.id.txt_your_name);


        btnOk = view.findViewById(R.id.btnOk);
        btnCancel = view.findViewById(R.id.btnCancel);
        getDialog().setTitle("Hello");

        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFinishEditDialog(mEditText.getText().toString());
                getDialog().dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (LabelDialogListener) context;
    }
}
