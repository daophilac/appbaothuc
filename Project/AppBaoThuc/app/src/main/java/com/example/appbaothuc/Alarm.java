package com.example.appbaothuc;

import java.util.List;

public class Alarm {
    private int idAlarm;
    private boolean enable;
    private int hour;
    private int minute;
    private List<Integer> listRepeatDay;
    private String ringtoneURL;
    private String ringtoneName;
    private String label;
    private int snoozeIn;
    private boolean vibrate;
    private int volume;
    private int challengeType;

    public Alarm(int idAlarm, boolean enable, int hour, int minute, List<Integer> listRepeatDay, String ringtoneURL, String ringtoneName, String label, int snoozeIn, boolean vibrate, int volume, int challengeType) {
        this.idAlarm = idAlarm;
        this.enable = enable;
        this.hour = hour;
        this.minute = minute;
        this.listRepeatDay = listRepeatDay;
        this.ringtoneURL = ringtoneURL;
        this.ringtoneName = ringtoneName;
        this.label = label;
        this.snoozeIn = snoozeIn;
        this.vibrate = vibrate;
        this.volume = volume;
        this.challengeType = challengeType;
    }

    public Alarm(int idAlarm, boolean enable, int hour, int minute, List<Integer> listRepeatDay) {
        this.idAlarm = idAlarm;
        this.enable = enable;
        this.hour = hour;
        this.minute = minute;
        this.listRepeatDay = listRepeatDay;
    }

    public int getIdAlarm() {
        return idAlarm;
    }

    public void setIdAlarm(int idAlarm) {
        this.idAlarm = idAlarm;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
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

    public List<Integer> getListRepeatDay() {
        return listRepeatDay;
    }

    public void setListRepeatDay(List<Integer> listRepeatDay) {
        this.listRepeatDay = listRepeatDay;
    }

    public String getRingtoneURL() {
        return ringtoneURL;
    }

    public void setRingtoneURL(String ringtoneURL) {
        this.ringtoneURL = ringtoneURL;
    }

    public String getRingtoneName() {
        return ringtoneName;
    }

    public void setRingtoneName(String ringtoneName) {
        this.ringtoneName = ringtoneName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getSnoozeIn() {
        return snoozeIn;
    }

    public void setSnoozeIn(int snoozeIn) {
        this.snoozeIn = snoozeIn;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(int challengeType) {
        this.challengeType = challengeType;
    }
}