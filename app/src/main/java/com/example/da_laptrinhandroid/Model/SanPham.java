package com.example.da_laptrinhandroid.Model;

public class SanPham {
    public int id;
    public String tensp;
    public int giatien;
    public String noidung;
    public int soluong;
    public String dvt;
    public String hinhsp;
    public int idlsp;

    public SanPham(int id, String tensp, int giatien, String noidung,int soluong, String dvt, String hinhsp, int idlsp) {
        this.id = id;
        this.tensp = tensp;
        this.giatien = giatien;
        this.noidung = noidung;
        this.soluong = soluong;//note
        this.dvt = dvt;
        this.hinhsp = hinhsp;//note
        this.idlsp = idlsp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGiatien() {
        return giatien;
    }

    public void setGiatien(int giatien) {
        this.giatien = giatien;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public int getIdlsp() {
        return idlsp;
    }

    public void setIdlsp(int idlsp) {
        this.idlsp = idlsp;
    }
}
