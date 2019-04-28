package com.example.quanlydonhang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quanlydonhang.chitietdondathang.CTDonDHActivity;
import com.example.quanlydonhang.dondathang.DonDH;
import com.example.quanlydonhang.dondathang.DonDHActivity;
import com.example.quanlydonhang.khachhang.KhachHangActivity;
import com.example.quanlydonhang.mathang.MatHangActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnDDH, buttonKhachHang, buttonCTDDH, buttonMatHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        //seed();
        //testGet();
    }
    void setControl(){
        btnDDH = findViewById(R.id.buttonDDH);
        buttonKhachHang = findViewById(R.id.buttonKhachHang);
        buttonCTDDH = findViewById(R.id.buttonCTDDH);
        buttonMatHang = findViewById(R.id.buttonMatHang);
        btnDDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), DonDHActivity.class);
                startActivity(intent);
            }
        });
        buttonKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), KhachHangActivity.class);
                startActivity(intent);
            }
        });
        buttonCTDDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), CTDonDHActivity.class);
                startActivity(intent);
            }
        });
        buttonMatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MatHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void testGet(){
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        String sql = "select * from tbDonDH where dondh_soddh = 1";
        Cursor curosr = db.rawQuery(sql, null);
        curosr.moveToNext();
        int soddh1 = curosr.getInt(0);
        String makh1 = curosr.getString(1);
        String ngaydh1 = curosr.getString(2);
        int songay1 = curosr.getInt(3);
        String tinhtrang1 = curosr.getString(4);
        curosr.close();
        DonDH donDH = new DonDH();
        donDH.setNgayDH(ngaydh1);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = donDH.getNgayDH();
        Toast.makeText(this, dateFormat.format(date), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, donDH.getNgayDH().toString(), Toast.LENGTH_LONG).show();
    }

    private void seed(){
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        String sqlFormat = "insert into tbDonDH(dondh_soddh, dondh_makh, dondh_ngaydh, dondh_songay, dondh_tinhtrang)" +
                "values(%d, '%s', '%s', %d, '%s')";
        String sql;

        int soddh1 = 1;
        String makh1 = "1";
        String ngaydh1 = "30-04-2019 12:12:12:121";
        int songay1 = 1;
        String tinhtrang1 = "1";

        int soddh2 = 2;
        String makh2 = "2";
        String ngaydh2 = "30-04-2019 12:13:12:121";
        int songay2 = 2;
        String tinhtrang2 = "2";
        sql = String.format(sqlFormat, soddh1, makh1, ngaydh1, songay1, tinhtrang1);
        db.execSQL(sql);

        sql = String.format(sqlFormat, soddh2, makh2, ngaydh2, songay2, tinhtrang2);
        db.execSQL(sql);
    }
}
