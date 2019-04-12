package com.example.appbaothuc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
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
        listAlarmColumn.add("RingtoneURL");
        listAlarmColumn.add("RingtoneName");
        listAlarmColumn.add("Label");
        listAlarmColumn.add("CanSnooze");
        listAlarmColumn.add("SnoozeTime");
        listAlarmColumn.add("SnoozeIn");
        listAlarmColumn.add("Vibrate");
        listAlarmColumn.add("MaxVolume");
        listAlarmColumn.add("Volume");
        listAlarmColumn.add("ChallengeType");

        sql = "create table if not exists Alarm(" +
                "IdAlarm integer primary key autoincrement," +
                "Enable binary not null," +
                "Hour integer not null," +
                "Minute integer not null," +
                "Monday binary not null," +
                "Tuesday binary not null," +
                "Wednesday binary not null," +
                "Thursday binary not null," +
                "Friday binary not null," +
                "Saturday binary not null," +
                "Sunday binary not null," +
                "RingtoneURL nvarchar(256)," +
                "RingtoneName nvarchar(256)," +
                "Label nvarchar(256)," +
                "CanSnooze binary," +
                "SnoozeTime integer," +
                "SnoozeIn integer," +
                "Vibrate binary," +
                "MaxVolume binary," +
                "Volume integer," +
                "ChallengeType integer)";
        db.execSQL(sql);
    }
    public void insertAlarm(boolean enable, int hour, int minute, List<Integer> listRepeatDay, String ringtoneURL, String ringtoneName,
                             String label, boolean canSnooze, int snoozeTime, int snoozeIn, boolean vibrate, boolean maxVolume, int volume, int challengeType) {
        sqlFormat = "insert into Alarm (" +
                " Enable, Hour, Minute," +
                " Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday," +
                " RingtoneURL, RingtoneName, Label, CanSnooze, SnoozeTime, SnoozeIn," +
                " Vibrate, MaxVolume, Volume, ChallengeType)" +
                " values (" +
                " '%s', %d, %d," +
                " %d, %d, %d, %d, %d, %d, %d," +
                " '%s', '%s', '%s', %d, %d, %d," +
                " %d, %d, %d, %d)";
        sql = String.format(sqlFormat,
                booleanToInt(enable), hour, minute,
                listRepeatDay.get(0), listRepeatDay.get(1), listRepeatDay.get(2), listRepeatDay.get(3), listRepeatDay.get(4), listRepeatDay.get(5), listRepeatDay.get(6),
                ringtoneURL, ringtoneName, label, booleanToInt(canSnooze), snoozeTime, snoozeIn,
                booleanToInt(vibrate), booleanToInt(maxVolume), volume, challengeType);
        db.execSQL(sql);
    }
    public void insertAlarm(boolean enable, int hour, int minute, List<Integer> listRepeatDay) {
        sqlFormat = "insert into Alarm (" +
                " Enable, Hour, Minute," +
                " Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday)" +
                " values (" +
                " '%s', %d, %d," +
                " %d, %d, %d, %d, %d, %d, %d)";
        sql = String.format(sqlFormat,
                booleanToInt(enable), hour, minute,
                listRepeatDay.get(0), listRepeatDay.get(1), listRepeatDay.get(2), listRepeatDay.get(3), listRepeatDay.get(4), listRepeatDay.get(5), listRepeatDay.get(6));
        db.execSQL(sql);
    }
    private int booleanToInt(boolean input){
        return input ? 1 : 0;
    }
    public HashMap<String, String> getTodayNextAlarm(){
        Calendar now = Calendar.getInstance();
        int nowWeekDay = now.get(Calendar.DAY_OF_WEEK);
        int nowHour = now.get(Calendar.HOUR_OF_DAY);
        int nowMinute = now.get(Calendar.MINUTE);
        String dayOfWeek = getDayOfWeekInString(nowWeekDay);
        sqlFormat = "select * from Alarm" +
                " where %s = 1 and ((Hour > %d) or (Hour = %d and Minute > %d))" +
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
                " where %s = 1 and ((Hour > %d) or (Hour = %d and Minute > %d))" +
                " order by Hour, Minute";
        sql = String.format(sqlFormat, weekDayToCompare, nowHour, nowHour, nowMinute);
        cursor = db.rawQuery(sql, null);
        if(cursor.moveToNext()){
            alarmProperty = buildAlarmProperty(cursor, true);
            alarmProperty.put("DayOfWeek", String.valueOf(nowWeekDay));
            return alarmProperty;
        }



        sqlFormat = "select * from Alarm" +
                " where %s = 1" +
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