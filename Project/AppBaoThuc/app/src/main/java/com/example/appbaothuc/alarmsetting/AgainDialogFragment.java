package com.example.appbaothuc.alarmsetting;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.appbaothuc.R;

public class AgainDialogFragment extends DialogFragment {
    private Button btnOK, btnCancel;
    private RadioGroup radioGroupAgain;

    public interface AgainDialogListener{
        void onFinishChoiceDialog(Integer input);
    }
    private AgainDialogListener listener;
    public AgainDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View viewDialog = inflater.inflate(R.layout.fragment_again_dialog, container);
        btnOK = viewDialog.findViewById(R.id.btnOK);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
        radioGroupAgain = viewDialog.findViewById(R.id.radioGroupAgain);

        getDialog().setTitle("Hello");
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroupAgain.getCheckedRadioButtonId(); //get text on RadioButton checked
                RadioButton radioButton = viewDialog.findViewById(selectedId);
                Integer index = radioGroupAgain.indexOfChild(radioButton), input;
                if(index == 0) input = 0;
                else if(index == 1) input = 1;
                else input = (index - 1) * 5;
                 listener.onFinishChoiceDialog(input);
                getDialog().dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return viewDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (AgainDialogListener) context;
    }

}