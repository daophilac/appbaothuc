package com.example.appbaothuc;

public class Alarm {
    private int hour;
    private int minute;
    private int alarmType;
    private boolean enable;

    public Alarm(int hour, int minute, int alarmType, boolean enable) {
        this.hour = hour;
        this.minute = minute;
        this.alarmType = alarmType;
        this.enable = enable;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
