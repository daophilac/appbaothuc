package com.example.appbaothuc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "APPBAOTHUC.db";
    private static final CursorFactory CURSOR_FACTORY = null;
    private static final int DATABASE_VERSION = 1;
    private Context context;
    private String databaseName;
    private CursorFactory cursorFactory;
    private int databaseVersion;
    private SQLiteDatabase db;
    private String sql;
    private String sqlFormat;
    private List<String> listAlarmColumn;

    public DatabaseHandler(Context context, String databaseName, CursorFactory cursorFactory, int databaseVersion) {
        super(context, databaseName, cursorFactory, databaseVersion);
        this.context = context;
        this.databaseName = databaseName;
        this.cursorFactory = cursorFactory;
        this.databaseVersion = databaseVersion;
        db = getWritableDatabase();
        initializeTables();
    }
    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, CURSOR_FACTORY, DATABASE_VERSION);
        db = getWritableDatabase();
        initializeTables();
    }
    private void initializeTables() {
        listAlarmColumn = new ArrayList<>();
        listAlarmColumn.add("IdAlarm");
        listAlarmColumn.add("Enable");
        listAlarmColumn.add("Hour");
        listAlarmColumn.add("Minute");
        listAlarmColumn.add("Monday");
        listAlarmColumn.add("Tuesday");
        listAlarmColumn.add("Wednesday");
        listAlarmColumn.add("Thursday");
        listAlarmColumn.add("Friday");
        listAlarmColumn.add("Saturday");
        listAlarmColumn.add("Sunday");
        listAlarmColumn.add("RingtoneUrl");
        listAlarmColumn.add("RingtoneName");
        listAlarmColumn.add("Label");
        listAlarmColumn.add("Vibrate");
        listAlarmColumn.add("SnoozeTime");
        listAlarmColumn.add("Volume");
        listAlarmColumn.add("ChallengeType");

        sql = "create table if not exists Alarm(" +
                "IdAlarm integer primary key autoincrement," +
                "Enable bit not null," +
                "Hour integer not null," +
                "Minute integer not null," +
                "Monday bit not null," +
                "Tuesday bit not null," +
                "Wednesday bit not null," +
                "Thursday bit not null," +
                "Friday bit not null," +
                "Saturday bit not null," +
                "Sunday bit not null," +
                "RingtoneUrl nvarchar(256)," +
                "RingtoneName nvarchar(256)," +
                "Label nvarchar(256)," +
                "Vibrate bit," +
                "SnoozeTime integer," +
                "Volume integer," +
                "ChallengeType integer)";
        db.execSQL(sql);
    }
    public void insertAlarm(boolean enable, int hour, int minute, List<Boolean> listRepeatDay, String ringtoneUrl, String ringtoneName,
                            boolean vibrate, String label, int snoozeTime, int volume, int challengeType) {
        sqlFormat = "insert into Alarm (" +
                " Enable, Hour, Minute," +
                " Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday," +
                " RingtoneUrl, RingtoneName, Label, Vibrate, SnoozeTime, Volume, ChallengeType)" +
                " values (" +
                " '%b', %d, %d," +
                " '%b', '%b', '%b', '%b', '%b', '%b', '%b'," +
                " '%s', '%s', '%s', '%b', %d, %d, %d)";
        sql = String.format(sqlFormat,
                enable, hour, minute,
                listRepeatDay.get(0), listRepeatDay.get(1), listRepeatDay.get(2), listRepeatDay.get(3), listRepeatDay.get(4), listRepeatDay.get(5), listRepeatDay.get(6),
                ringtoneUrl, ringtoneName, label, vibrate, snoozeTime, volume, challengeType);
        db.execSQL(sql);
    }
    public void insertAlarm(boolean enable, int hour, int minute, List<Boolean> listRepeatDay) {
        sqlFormat = "insert into Alarm (" +
                " Enable, Hour, Minute," +
                " Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday)" +
                " values (" +
                " '%b', %d, %d," +
                " '%b', '%b', '%b', '%b', '%b', '%b', '%b')";
        sql = String.format(sqlFormat,
                enable, hour, minute,
                listRepeatDay.get(0), listRepeatDay.get(1), listRepeatDay.get(2), listRepeatDay.get(3), listRepeatDay.get(4), listRepeatDay.get(5), listRepeatDay.get(6));
        db.execSQL(sql);
    }
    public void insertAlarm(Alarm alarm){
        boolean enable = alarm.isEnable();
        int hour = alarm.getHour();
        int minute = alarm.getMinute();
        List<Boolean> listRepeatDay = alarm.getListRepeatDay();
        String ringtoneUrl = alarm.getRingtoneUrl();
        String ringtoneName = alarm.getRingtoneName();
        String label = alarm.getLabel();
        boolean vibrate = alarm.isVibrate();
        int snoozeTime = alarm.getSnoozeTime();
        int volume = alarm.getVolume();
        int challengeType = alarm.getChallengeType();

        sqlFormat = "insert into Alarm (" +
                " Enable, Hour, Minute," +
                " Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday," +
                " RingtoneUrl, RingtoneName, Label, Vibrate, SnoozeTime, Volume, ChallengeType)" +
                " values (" +
                " '%b', %d, %d," +
                " '%b', '%b', '%b', '%b', '%b', '%b', '%b'," +
                " '%s', '%s', '%s', '%b', %d, %d, %d)";
        sql = String.format(sqlFormat,
                enable, hour, minute,
                listRepeatDay.get(0), listRepeatDay.get(1), listRepeatDay.get(2), listRepeatDay.get(3), listRepeatDay.get(4), listRepeatDay.get(5), listRepeatDay.get(6),
                ringtoneUrl, ringtoneName, label, vibrate, snoozeTime, volume, challengeType);
        db.execSQL(sql);
    }
    public HashMap<String, String> getLastAlarm(){
        sql = "select * from Alarm order by IdAlarm desc limit 1";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        return buildAlarmProperty(cursor, true);
    }
    public HashMap<String, String> getTodayNextAlarm(){
        Calendar now = Calendar.getInstance();
        int nowWeekDay = now.get(Calendar.DAY_OF_WEEK);
        int nowHour = now.get(Calendar.HOUR_OF_DAY);
        int nowMinute = now.get(Calendar.MINUTE);
        String dayOfWeek = getDayOfWeekInString(nowWeekDay);
        sqlFormat = "select * from Alarm" +
                " where %s = 'true' and ((Hour > %d) or (Hour = %d and Minute > %d))" +
                " order by Hour, Minute";
        sql = String.format(sqlFormat, dayOfWeek, nowHour, nowHour, nowMinute);
        Cursor cursor = db.rawQuery(sql, null);
        if(!cursor.moveToNext()){
            return null;
        }
        return buildAlarmProperty(cursor, true);
    }
    public HashMap<String, String> getTheNearestAlarm(){
        Cursor cursor;
        HashMap<String, String> alarmProperty;

        Calendar now = Calendar.getInstance();
        int nowWeekDay = now.get(Calendar.DAY_OF_WEEK);
        int nowHour = now.get(Calendar.HOUR_OF_DAY);
        int nowMinute = now.get(Calendar.MINUTE);

        String weekDayToCompare = getDayOfWeekInString(nowWeekDay);
        sqlFormat = "select * from Alarm" +
                " where %s = 'true' and ((Hour > %d) or (Hour = %d and Minute > %d))" +
                " order by Hour, Minute";
        sql = String.format(sqlFormat, weekDayToCompare, nowHour, nowHour, nowMinute);
        cursor = db.rawQuery(sql, null);
        if(cursor.moveToNext()){
            alarmProperty = buildAlarmProperty(cursor, true);
            alarmProperty.put("DayOfWeek", String.valueOf(nowWeekDay));
            return alarmProperty;
        }



        sqlFormat = "select * from Alarm" +
                " where %s = 'true'" +
                " order by Hour, Minute";
        for(int i = nowWeekDay + 1; i <= 7; i++){
            weekDayToCompare = getDayOfWeekInString(i);
            sql = String.format(sqlFormat, weekDayToCompare);
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                alarmProperty = buildAlarmProperty(cursor, true);
                alarmProperty.put("DayOfWeek", String.valueOf(i));
                return alarmProperty;
            }
        }



        for(int i = 8; i < nowWeekDay + 7; i++){
            int mod = i % 7;
            weekDayToCompare = getDayOfWeekInString(mod);
            sql = String.format(sqlFormat, weekDayToCompare);
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                alarmProperty = buildAlarmProperty(cursor, true);
                alarmProperty.put("DayOfWeek", String.valueOf(mod));
                return alarmProperty;
            }
        }
        return null;
    }
    private HashMap<String, String> buildAlarmProperty(Cursor cursor, boolean closeCursorWhenDone){
        if(cursor.isBeforeFirst()){
            throw new RuntimeException("Cursor is at position -1. Either there is not any row in the query or the cursor has not moved to the first row in the result set yet.");
        }
        HashMap<String, String> alarmProperty = new HashMap<>();
        for(int i = 0; i < listAlarmColumn.size(); i++){
            alarmProperty.put(listAlarmColumn.get(i), cursor.getString(i));
        }
        if(closeCursorWhenDone){
            cursor.close();
        }
        return alarmProperty;
    }
    public String getDayOfWeekInString(int dayOfWeekInInteger){
        switch(dayOfWeekInInteger){
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return "";
        }
    }
    public boolean checkIfThereIsAnyAlarm(){
        sql = "select * from Alarm";
        Cursor cursor = db.rawQuery(sql, null);
        if(!cursor.moveToNext()){
            return false;
        }
        else{
            cursor.close();
            return true;
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}