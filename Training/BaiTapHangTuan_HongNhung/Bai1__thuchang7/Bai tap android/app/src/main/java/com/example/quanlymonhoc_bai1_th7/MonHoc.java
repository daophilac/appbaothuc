package com.example.quanlymonhoc_bai1_th7;

public class MonHoc {
    private int img;
    private String ma, ten, sotiet;

    public MonHoc() {
    }

    public MonHoc(int img, String ma, String ten, String sotiet) {
        this.img = img;
        this.ma = ma;
        this.ten = ten;
        this.sotiet = sotiet;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSotiet() {
        return sotiet;
    }

    public void setSotiet(String sotiet) {
        this.sotiet = sotiet;
    }
}

