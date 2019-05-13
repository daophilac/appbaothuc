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

public class MuteAlarmInDialogFragment extends DialogFragment {
    private NumberPicker numberPickerMuteAlarmFor;
    private Button buttonCancel;
    private Button buttonOk;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_mute_alarm_in, container, false);
        this.numberPickerMuteAlarmFor = view.findViewById(R.id.number_picker_mute_alarm_for);
        this.buttonCancel = view.findViewById(R.id.button_give_up);
        this.buttonOk = view.findViewById(R.id.button_ok);

        this.numberPickerMuteAlarmFor.setMinValue(30);
        this.numberPickerMuteAlarmFor.setMaxValue(300);
        this.numberPickerMuteAlarmFor.setValue(AppSettingFragment.muteAlarmIn);
        this.numberPickerMuteAlarmFor.setWrapSelectorWheel(false);
        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        this.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = numberPickerMuteAlarmFor.getValue();
                AppSettingFragment.muteAlarmIn = value;
                AppSettingFragment.btnMute.setText(value + " giây");
                getDialog().dismiss();
            }
        });
        return view;
    }
}
