package com.example.quanlydonhang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quanlydonhang.chitietdondathang.CTDonDH;
import com.example.quanlydonhang.dondathang.DonDH;
import com.example.quanlydonhang.khachhang.KhachHang;
import com.example.quanlydonhang.mathang.MatHang;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static String DB_NAME = "dbQuanLyDonHang.db";
    public static Integer DB_VERSION = 1;
    private SQLiteDatabase db;
    private String myFormat = "dd-MM-yyyy HH:mm:ss:SSS";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db = sqLiteDatabase;
        String scriptTBKhachHang = "CREATE TABLE IF NOT EXISTS KHACHHANG(" +
                "MAKH" + " Varchar(20) PRIMARY KEY NOT NULL, " +
                "TENKH" + " Nvarchar(50) NOT NULL)";
        db.execSQL(scriptTBKhachHang);
        String scriptTBDonDH = "CREATE TABLE IF NOT EXISTS DONDH(" +
                "SODDH" + " INTEGER PRIMARY KEY NOT NULL, " +
                "MAKH" + " Varchar(20) NOT NULL," +
                "NGAYDH" + " DATETIME NOT NULL," +
                "SONGAY" + " INTEGER NOT NULL," +
                "TINHTRANG" + " Nvarchar(100)," +
                "FOREIGN KEY (MAKH) REFERENCES KHACHHANG(MAKH) ON DELETE CASCADE)";
        db.execSQL(scriptTBDonDH);
        String scriptTBMatHang = "CREATE TABLE IF NOT EXISTS MATHANG(" +
                "MAHG" + " Varchar(20) PRIMARY KEY NOT NULL, " +
                "TENHG" + " Nvarchar(50) NOT NULL," +
                "DACDIEM" + " TEXT," +
                "DVT" + " Nvarchar(40) NOT NULL," +
                "DONGIA" + " INTEGER NOT NULL)";
        db.execSQL(scriptTBMatHang);
        String scriptTBCTDonDH = "CREATE TABLE IF NOT EXISTS CTDONDH(" +
                "SODDH" + " INTEGER, " +
                "MAHG" + " Varchar(20)," +
                "SLDAT" + " INTEGER NOT NULL," +
                "PRIMARY KEY(SODDH, MAHG)," +
                "FOREIGN KEY (SODDH) REFERENCES DONDH(SODDH) ON DELETE CASCADE," +
                "FOREIGN KEY (MAHG) REFERENCES MATHANG(MAHG) ON DELETE CASCADE )";
        db.execSQL(scriptTBCTDonDH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "DONDH");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "KHACHHANG");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "MATHANG");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "CTDONDH");
        onCreate(sqLiteDatabase);
    }

    //ĐƠN ĐẶT HÀNG
    public void getDonDHS(ArrayList<DonDH> donDHS) {
        String sql = "select * from DONDH";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                DonDH donDH = new DonDH();
                donDH.setSoDH(cursor.getInt(cursor.getColumnIndex("SODDH")));
                donDH.setMaKH(cursor.getString(cursor.getColumnIndex("MAKH")));
                String a = cursor.getString(cursor.getColumnIndex("NGAYDH"));

                donDH.setNgayDH(a);
                donDH.setSoNgay(cursor.getInt(cursor.getColumnIndex("SONGAY")));
                donDH.setTinhTrang(cursor.getString(cursor.getColumnIndex("TINHTRANG")));
                donDHS.add(donDH);
            } while (cursor.moveToNext());
        }
    }

    public void saveDonDHs(DonDH donDH) {

        ContentValues values = new ContentValues();
        values.put("SODDH", donDH.getSoDH());
        values.put("MAKH", donDH.getMaKH());
        String dateTime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat);
        dateTime = dateFormat.format(donDH.getNgayDH());
        values.put("NGAYDH", dateTime);
        values.put("SONGAY", donDH.getSoNgay());
        values.put("TINHTRANG", donDH.getTinhTrang());
        db.insert("DONDH", null, values);
    }

    public void deleteDonDH(int soDonDH) {
        String sql = "delete from DONDH where SODDH=" + soDonDH;
        db.execSQL(sql);
    }

    public void updateDonDH(DonDH donDH) {
        ContentValues values = new ContentValues();
        values.put("SODDH", donDH.getSoDH());
        values.put("MAKH", donDH.getMaKH());
        String dateTime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat);
        dateTime = dateFormat.format(donDH.getNgayDH());
        values.put("NGAYDH", dateTime);
        values.put("SONGAY", donDH.getSoNgay());
        values.put("TINHTRANG", donDH.getTinhTrang());
        db.update("DONDH", values, "SODDH" + "=" + donDH.getSoDH(), null);
    }

    public void getMaKH(ArrayList<String> maKHs) {
        maKHs.add("Chọn Mã Khách Hàng");
        String sql = "select * from KHACHHANG";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                maKHs.add(cursor.getString(cursor.getColumnIndex("MAKH")));
            } while (cursor.moveToNext());
        }
    }

    public Boolean getCTDonDH(Integer soDDH) {
        String sql = "select * from CTDONDH where SODDH =" + soDDH;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public void findBySoDDH(ArrayList<DonDH> donDHS, Integer soDDH) {
        String sql = "select * from DONDH where SODDH LIKE '%" + soDDH + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                DonDH donDH = new DonDH();
                donDH.setSoDH(cursor.getInt(cursor.getColumnIndex("SODDH")));
                donDH.setMaKH(cursor.getString(cursor.getColumnIndex("MAKH")));
                String a = cursor.getString(cursor.getColumnIndex("NGAYDH"));

                donDH.setNgayDH(a);
                donDH.setSoNgay(cursor.getInt(cursor.getColumnIndex("SONGAY")));
                donDH.setTinhTrang(cursor.getString(cursor.getColumnIndex("TINHTRANG")));
                donDHS.add(donDH);
            } while (cursor.moveToNext());
        }
    }


    //KHÁCH HÀNG
    public void getKhachHangs(ArrayList<KhachHang> khachHangs) {
        String sql = "select * from KHACHHANG";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                KhachHang khachHang = new KhachHang();
                khachHang.setMaKH(cursor.getString(cursor.getColumnIndex("MAKH")));
                khachHang.setTenKH(cursor.getString(cursor.getColumnIndex("TENKH")));
                khachHangs.add(khachHang);
            } while (cursor.moveToNext());
        }
    }

    public void saveKhachHangs(KhachHang khachHang) {
        ContentValues values = new ContentValues();
        values.put("MAKH", khachHang.getMaKH());
        values.put("TENKH", khachHang.getTenKH());
        db.insert("KHACHHANG", null, values);
    }

    public Boolean getDDH(String maKH) {
        String sql = "select * from DONDH where MAKH ='" + maKH + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public void deleteKhachHang(String maKH) {
        String sql = "delete from KHACHHANG where MAKH='" + maKH + "'";
        db.execSQL(sql);
    }

    public void updateKhachHang(KhachHang khachHang) {
        ContentValues values = new ContentValues();
        values.put("MAKH", khachHang.getMaKH());
        values.put("TENKH", khachHang.getTenKH());
        db.update("KHACHHANG", values, "MAKH" + "='" + khachHang.getMaKH() + "'", null);
    }

    public void findByMaKH(ArrayList<KhachHang> khachHangs, String maKH) {
        String sql = "select * from KHACHHANG where MAKH LIKE '%" + maKH + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                KhachHang khachHang = new KhachHang();
                khachHang.setMaKH(cursor.getString(cursor.getColumnIndex("MAKH")));
                khachHang.setTenKH(cursor.getString(cursor.getColumnIndex("TENKH")));
                khachHangs.add(khachHang);
            } while (cursor.moveToNext());
        }
    }

    //MẶT HÀNG
    public void getMatHangs(ArrayList<MatHang> matHangs) {
        String sql = "select * from MATHANG";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                MatHang matHang = new MatHang();
                matHang.setMAHG(cursor.getString(cursor.getColumnIndex("MAHG")));
                matHang.setTENHG(cursor.getString(cursor.getColumnIndex("TENHG")));
                matHang.setDACDIEM(cursor.getString(cursor.getColumnIndex("DACDIEM")));
                matHang.setDVT(cursor.getString(cursor.getColumnIndex("DVT")));
                matHang.setDONGIA(Integer.parseInt(cursor.getString(cursor.getColumnIndex("DONGIA"))));
                matHangs.add(matHang);
            } while (cursor.moveToNext());
        }
    }

    public void saveMatHang(MatHang matHang) {
        ContentValues values = new ContentValues();
        values.put("MAHG", matHang.getMAHG());
        values.put("TENHG", matHang.getTENHG());
        values.put("DACDIEM", matHang.getDACDIEM());
        values.put("DVT", matHang.getDVT());
        values.put("DONGIA", matHang.getDONGIA());
        db.insert("MATHANG", null, values);
    }

    public Boolean getMaMatHang(String maHG) {
        String sql = "select * from MATHANG where MAHG='" + maHG + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() == 0) {
            return true;
        }
        return false;
    }

    public void deleteMatHang(String maHG) {
        String sql = "delete from MATHANG where MAHG='" + maHG + "'";
        db.execSQL(sql);
    }

    public void updateMatHang(MatHang matHang) {
        ContentValues values = new ContentValues();
        values.put("MAHG", matHang.getMAHG());
        values.put("TENHG", matHang.getTENHG());
        values.put("DACDIEM", matHang.getDACDIEM());
        values.put("DVT", matHang.getDVT());
        values.put("DONGIA", matHang.getDONGIA());
        db.update("MATHANG", values, "MAHG='" + matHang.getMAHG() + "'", null);
    }

    public Boolean getCTDonDH(String maHG) {
        String sql = "select * from CTDONDH where MAHG='" + maHG + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public void findByMaHG(ArrayList<MatHang> matHangs, String maKH) {

        String sql = "select * from MATHANG where MAHG LIKE '%" + maKH + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                MatHang matHang = new MatHang();
                matHang.setMAHG(cursor.getString(cursor.getColumnIndex("MAHG")));
                matHang.setTENHG(cursor.getString(cursor.getColumnIndex("TENHG")));
                matHang.setDACDIEM(cursor.getString(cursor.getColumnIndex("DACDIEM")));
                matHang.setDVT(cursor.getString(cursor.getColumnIndex("DVT")));
                matHang.setDONGIA(Integer.parseInt(cursor.getString(cursor.getColumnIndex("DONGIA"))));
                matHangs.add(matHang);
            } while (cursor.moveToNext());
        }
    }


    //CHI TIẾT ĐƠN ĐẶT HÀNG
    public void getCTDonDHs(ArrayList<CTDonDH> ctDonDHS) {
        String sql = "select * from CTDONDH";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                CTDonDH ctDonDH = new CTDonDH();
                ctDonDH.setSODDH(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SODDH"))));
                ctDonDH.setMAHG(cursor.getString(cursor.getColumnIndex("MAHG")));
                ctDonDH.setSLDAT(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SLDAT"))));
                ctDonDHS.add(ctDonDH);
            } while (cursor.moveToNext());
        }
    }

    public ArrayList<CTDonDH> getCTDonDHWhereSoDDH(Integer soDDH) {
        ArrayList<CTDonDH> ctDonDHS = new ArrayList<>();
        String sql = "select * from CTDONDH where SODDH =" + soDDH;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                CTDonDH ctDonDH = new CTDonDH();
                ctDonDH.setSODDH(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SODDH"))));
                ctDonDH.setMAHG(cursor.getString(cursor.getColumnIndex("MAHG")));
                ctDonDH.setSLDAT(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SLDAT"))));
                ctDonDHS.add(ctDonDH);
            } while (cursor.moveToNext());
        }
        return ctDonDHS;
    }

    public ArrayList<CTDonDH> getCTDonDHWhereMaHG(String maHG) {
        ArrayList<CTDonDH> ctDonDHS = new ArrayList<>();
        String sql = "select * from CTDONDH where MAHG ='" + maHG + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                CTDonDH ctDonDH = new CTDonDH();
                ctDonDH.setSODDH(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SODDH"))));
                ctDonDH.setMAHG(cursor.getString(cursor.getColumnIndex("MAHG")));
                ctDonDH.setSLDAT(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SLDAT"))));
                ctDonDHS.add(ctDonDH);
            } while (cursor.moveToNext());
        }
        return ctDonDHS;
    }

    public void saveCTDonDH(CTDonDH ctDonDH) {
        ContentValues values = new ContentValues();
        values.put("SODDH", ctDonDH.getSODDH());
        values.put("MAHG", ctDonDH.getMAHG());
        values.put("SLDAT", ctDonDH.getSLDAT());
        db.insert("CTDONDH", null, values);
    }

    public void deleteCTDonDH(Integer soDDH, String maHG) {
        String sql = "delete from CTDONDH where SODDH=" + soDDH + " and MAHG='" + maHG + "'";
        db.execSQL(sql);
    }

    public void updateCTDonDH(CTDonDH ctDonDH) {
        ContentValues values = new ContentValues();
        values.put("SODDH", ctDonDH.getSODDH());
        values.put("MAHG", ctDonDH.getMAHG());
        values.put("SLDAT", ctDonDH.getSLDAT());
        db.update("CTDONDH", values, "SODDH" + "=" + ctDonDH.getSODDH() + " and MAHG='" + ctDonDH.getMAHG() + "'", null);
    }

    public void getListSoDDH(ArrayList<String> arr) {
        arr.add("Chọn Số Đơn Đặt Hàng");
        String sql = "select * from DONDH";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                arr.add(cursor.getString(cursor.getColumnIndex("SODDH")));
            } while (cursor.moveToNext());
        }

    }

    public void getListMaMH(ArrayList<String> arr) {
        arr.add("Chọn Mã Mặt Hàng");
        String sql = "select * from MATHANG";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                arr.add(cursor.getString(cursor.getColumnIndex("MAHG")));
            } while (cursor.moveToNext());
        }
    }

    public ArrayList getCTDDHTheoSoDDH(int soDDH) {
        ArrayList<CTDonDH> arrayList = new ArrayList<>();
        String sql = "select * from CTDONDH where SODDH =" + soDDH;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                CTDonDH ctDonDH = new CTDonDH();
                ctDonDH.setSODDH(cursor.getInt(cursor.getColumnIndex("SODDH")));
                ctDonDH.setMAHG(cursor.getString(cursor.getColumnIndex("MAHG")));
                ctDonDH.setSLDAT(cursor.getInt(cursor.getColumnIndex("SLDAT")));
                arrayList.add(ctDonDH);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public void getMatHangBanNhieuNhat(ArrayList<MatHang> arrayList) {
        String sql = "CREATE TEMPORARY TABLE TEMPMH AS SELECT MAHG, SUM(SLDAT) as TONG FROM CTDONDH GROUP BY MAHG order by TONG DESC limit 3";
        db.execSQL(sql);
        sql = "select * from MATHANG, TEMPMH where MATHANG.MAHG = TEMPMH.MAHG";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                MatHang matHang = new MatHang();
                matHang.setMAHG(cursor.getString(cursor.getColumnIndex("MAHG")));
                matHang.setTENHG(cursor.getString(cursor.getColumnIndex("TENHG")));
                matHang.setDACDIEM(cursor.getString(cursor.getColumnIndex("DACDIEM")));
                matHang.setDVT(cursor.getString(cursor.getColumnIndex("DVT")));
                matHang.setDONGIA(Integer.parseInt(cursor.getString(cursor.getColumnIndex("DONGIA"))));
                arrayList.add(matHang);
            } while (cursor.moveToNext());
        }
        db.execSQL("DROP TABLE " + "TEMPMH");
    }
    public void getKhachHangMuaNhieuNhat(ArrayList<KhachHang> arrayList) {
        String sql = "CREATE TEMPORARY TABLE TEMPDDH AS SELECT SODDH, SUM(SLDAT) as TONG FROM CTDONDH GROUP BY SODDH order by TONG DESC limit 3";
        db.execSQL(sql);
        sql = "CREATE TEMPORARY TABLE TEMPKH AS SELECT DONDH.MAKH from DONDH, TEMPDDH WHERE DONDH.SODDH = TEMPDDH.SODDH";
        db.execSQL(sql);
        sql = "SELECT * from KHACHHANG, TEMPKH where KHACHHANG.MAKH = TEMPKH.MAKH";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                KhachHang khachHang = new KhachHang();
                khachHang.setMaKH(cursor.getString(cursor.getColumnIndex("MAKH")));
                khachHang.setTenKH(cursor.getString(cursor.getColumnIndex("TENKH")));
                arrayList.add(khachHang);
            } while (cursor.moveToNext());
        }
        db.execSQL("DROP TABLE " + "TEMPDDH");
        db.execSQL("DROP TABLE " + "TEMPKH");
    }
}
