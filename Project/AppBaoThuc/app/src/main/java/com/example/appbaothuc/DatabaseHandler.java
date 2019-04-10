package com.example.appbaothuc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.HashMap;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "APPBAOTHUC";
    private static final CursorFactory CURSOR_FACTORY = null;
    private static final int DATABASE_VERSION = 1;
    private Context context;
    private String databaseName;
    private CursorFactory cursorFactory;
    private int databaseVersion;
    private SQLiteDatabase db;
    private String sql;
    private String sqlFormat;

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
        sql = "create table if not exists Alarm(" +
                "IdAlarm integer primary key autoincrement," +
                "Label nvarchar(256)," +
                "Hour integer," +
                "Minute integer)";
        db.execSQL(sql);
    }

    public void insertAlarm(String label, int hour, int minute) {
        sqlFormat = "insert into Alarm (Label, Hour, Minute)" +
                "values ('%s', %d, %d)";
        sql = String.format(sqlFormat, label, hour, minute);
        db.execSQL(sql);
    }
    public String getNextAlarmFromNow(){
        Calendar now = Calendar.getInstance();
        int nowHour = now.get(Calendar.HOUR_OF_DAY);
        int nowMinute = now.get(Calendar.MINUTE) + 1;
        sqlFormat = "select * from Alarm" +
                " where Hour >= %d and Minute >= %d" +
                " order by Hour, Minute";
        sql = String.format(sqlFormat, nowHour, nowMinute);
        Cursor cursor = db.rawQuery(sql, null);
        if(!cursor.moveToNext()){
            return null;
        }
        else{
            String label = cursor.getString(1);
            int hour = cursor.getInt(2);
            int minute = cursor.getInt(3);
            return label + "," + String.valueOf(hour) + "," + String.valueOf(minute);
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