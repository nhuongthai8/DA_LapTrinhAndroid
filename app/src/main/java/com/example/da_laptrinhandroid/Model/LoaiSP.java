package com.example.da_laptrinhandroid.Model;

public class LoaiSP {
    public int id;
    public String TenLSP;
    public String HinhLSP;

    public LoaiSP(int id, String tenLSP, String hinhLSP) {
        this.id = id;
        TenLSP = tenLSP;
        HinhLSP = hinhLSP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLSP() {
        return TenLSP;
    }

    public void setTenLSP(String tenLSP) {
        TenLSP = tenLSP;
    }

    public String getHinhLSP() {
        return HinhLSP;
    }

    public void setHinhLSP(String hinhLSP) {
        HinhLSP = hinhLSP;
    }
}
