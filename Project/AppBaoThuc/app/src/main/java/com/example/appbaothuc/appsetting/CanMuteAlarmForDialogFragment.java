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

public class CanMuteAlarmForDialogFragment extends DialogFragment {
    private NumberPicker numberPickerCanMuteAlarmFor;
    private Button buttonCancel;
    private Button buttonOk;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_can_mute_alarm_for, container, false);
        this.numberPickerCanMuteAlarmFor = view.findViewById(R.id.number_picker_can_mute_alarm_for);
        this.buttonCancel = view.findViewById(R.id.button_give_up);
        this.buttonOk = view.findViewById(R.id.button_ok);

        this.numberPickerCanMuteAlarmFor.setMinValue(0);
        this.numberPickerCanMuteAlarmFor.setMaxValue(5);
        this.numberPickerCanMuteAlarmFor.setValue(AppSettingFragment.canMuteAlarmFor);
        this.numberPickerCanMuteAlarmFor.setWrapSelectorWheel(false);
        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        this.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = numberPickerCanMuteAlarmFor.getValue();
                AppSettingFragment.canMuteAlarmFor = value;
                AppSettingFragment.btnCanMute.setText(value + " lần");
                getDialog().dismiss();
            }
        });
        return view;
    }
}
