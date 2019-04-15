package com.example.appbaothuc;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Alarm implements Parcelable {
    private int idAlarm;
    private boolean enable;
    private int hour;
    private int minute;
    private List<Boolean> listRepeatDay;
    private String ringtoneUrl;
    private String ringtoneName;
    private String label;
    private int snoozeTime;
    private boolean vibrate;
    private int volume;
    private int challengeType;

    // Non-database property
    private int dayOfWeek;

    public Alarm(int idAlarm, boolean enable, int hour, int minute, List<Boolean> listRepeatDay, String ringtoneUrl, String ringtoneName, String label, int snoozeTime, boolean vibrate, int volume, int challengeType) {
        this.idAlarm = idAlarm;
        this.enable = enable;
        this.hour = hour;
        this.minute = minute;
        this.listRepeatDay = listRepeatDay;
        this.ringtoneUrl = ringtoneUrl;
        this.ringtoneName = ringtoneName;
        this.label = label;
        this.snoozeTime = snoozeTime;
        this.vibrate = vibrate;
        this.volume = volume;
        this.challengeType = challengeType;
    }

    public Alarm(boolean enable, int hour, int minute, List<Boolean> listRepeatDay, String ringtoneUrl, String ringtoneName, String label, int snoozeTime, boolean vibrate, int volume, int challengeType) {
        this.enable = enable;
        this.hour = hour;
        this.minute = minute;
        this.listRepeatDay = listRepeatDay;
        this.ringtoneUrl = ringtoneUrl;
        this.ringtoneName = ringtoneName;
        this.label = label;
        this.snoozeTime = snoozeTime;
        this.vibrate = vibrate;
        this.volume = volume;
        this.challengeType = challengeType;
    }

    public Alarm(int idAlarm, boolean enable, int hour, int minute, List<Boolean> listRepeatDay) {
        this.idAlarm = idAlarm;
        this.enable = enable;
        this.hour = hour;
        this.minute = minute;
        this.listRepeatDay = listRepeatDay;
    }

    public Alarm(boolean enable, int hour, int minute, List<Boolean> listRepeatDay) {
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

    public List<Boolean> getListRepeatDay() {
        return listRepeatDay;
    }

    public void setListRepeatDay(List<Boolean> listRepeatDay) {
        this.listRepeatDay = listRepeatDay;
    }

    public String getRingtoneUrl() {
        return ringtoneUrl;
    }

    public void setRingtoneUrl(String ringtoneUrl) {
        this.ringtoneUrl = ringtoneUrl;
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

    public int getSnoozeTime() {
        return snoozeTime;
    }

    public void setSnoozeTime(int snoozeTime) {
        this.snoozeTime = snoozeTime;
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

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }








    protected Alarm(Parcel in) {
        idAlarm = in.readInt();
        enable = in.readByte() != 0;
        hour = in.readInt();
        minute = in.readInt();
        ringtoneUrl = in.readString();
        ringtoneName = in.readString();
        label = in.readString();
        snoozeTime = in.readInt();
        vibrate = in.readByte() != 0;
        volume = in.readInt();
        challengeType = in.readInt();
        dayOfWeek = in.readInt();
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idAlarm);
        dest.writeByte((byte) (enable ? 1 : 0));
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeString(ringtoneUrl);
        dest.writeString(ringtoneName);
        dest.writeString(label);
        dest.writeInt(snoozeTime);
        dest.writeByte((byte) (vibrate ? 1 : 0));
        dest.writeInt(volume);
        dest.writeInt(challengeType);
        dest.writeInt(dayOfWeek);
    }
}