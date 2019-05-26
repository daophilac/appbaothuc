package com.example.appbaothuc.appsetting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.appbaothuc.R;

public class AutoDismissAfterDialogFragment extends DialogFragment {
    private NumberPicker numberPickerAutoDismissAfter;
    private Button buttonCancel;
    private Button buttonOk;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_auto_dismiss_after, container, false);
        this.numberPickerAutoDismissAfter = view.findViewById(R.id.number_picker_auto_dismiss_after);
        this.buttonCancel = view.findViewById(R.id.button_give_up);
        this.buttonOk = view.findViewById(R.id.button_ok);

        this.numberPickerAutoDismissAfter.setMinValue(10);
        this.numberPickerAutoDismissAfter.setMaxValue(60);
        this.numberPickerAutoDismissAfter.setValue(AppSettingFragment.autoDismissAfter);
        this.numberPickerAutoDismissAfter.setWrapSelectorWheel(false);
        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        this.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = numberPickerAutoDismissAfter.getValue();
                AppSettingFragment.autoDismissAfter = value;
                AppSettingFragment.btnDismiss.setText(value + " phút");
                getDialog().dismiss();
            }
        });
        return view;
    }
}