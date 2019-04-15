package com.myour.appbaothucdinhcao;

public class Alarm {

    private boolean enableAlarm;
    private int hour;
    private int minute;
    private int imgAlarmType;

    public Alarm(boolean enableAlarm,int hour, int minute, int imgAlarmType) {
        this.enableAlarm=enableAlarm;
        this.hour = hour;
        this.minute = minute;
        this.imgAlarmType = imgAlarmType;
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

    public int getImgAlarmType() {
        return imgAlarmType;
    }

    public void setImgAlarmType(int imgAlarmType) {
        this.imgAlarmType = imgAlarmType;
    }

    public boolean isEnableAlarm() {
        return enableAlarm;
    }

    public void setEnableAlarm(boolean enableAlarm) {
        this.enableAlarm = enableAlarm;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "enableAlarm=" + enableAlarm +
                ", hour=" + hour +
                ", minute=" + minute +
                ", imgAlarmType=" + imgAlarmType +
                '}';
    }
}
