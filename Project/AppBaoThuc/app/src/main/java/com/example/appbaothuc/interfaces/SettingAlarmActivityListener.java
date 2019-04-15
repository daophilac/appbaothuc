package com.example.appbaothuc.interfaces;

import com.example.appbaothuc.Alarm;

public interface SettingAlarmActivityListener {
    void onAddNewAlarm(Alarm alarm);

    void onEditAlarm(Alarm alarm);
}
