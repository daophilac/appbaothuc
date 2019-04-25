package com.example.quanlydonhang.dondathang;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DonDH {
    private int soDH;
    private String maKH;
    private Date ngayDH;
    private int soNgay;
    private String tinhTrang;


    public DonDH(int soDH, String maKH, Date ngayDH, int soNgay, String tinhTrang) {
        this.soDH = soDH;
        this.maKH = maKH;
        this.ngayDH = ngayDH;
        this.soNgay = soNgay;
        this.tinhTrang = tinhTrang;
    }

    public DonDH() {
    }

    public int getSoDH() {
        return soDH;
    }

    public void setSoDH(int soDH) {
        this.soDH = soDH;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }


    public Date getNgayDH() {
        return ngayDH;
    }

    public String getStringNgayDH() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
        return dateFormat.format(this.getNgayDH());
    }


    public void setNgayDH(Date ngayDH) {
        this.ngayDH = ngayDH;
    }
    public void setNgayDH(String ngayDH){
        try {
            this.ngayDH = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS").parse(ngayDH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getSoNgay() {
        return soNgay;
    }


    public void setSoNgay(int soNgay) {
        this.soNgay = soNgay;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
