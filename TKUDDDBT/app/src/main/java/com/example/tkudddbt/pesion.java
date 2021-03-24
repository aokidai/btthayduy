package com.example.tkudddbt;

import java.io.Serializable;

public class pesion implements Serializable {
    private String id_Nguoi;
    private String Ten;
    private String SoDT;
    private String DiaChi;
    private String QueQuan;

    public pesion(String id_Nguoi, String ten, String soDT, String diaChi, String queQuan) {
        this.id_Nguoi = id_Nguoi;
        Ten = ten;
        SoDT = soDT;
        DiaChi = diaChi;
        QueQuan = queQuan;
    }

    public pesion(String ten, String soDT, String diaChi, String queQuan) {
        Ten = ten;
        SoDT = soDT;
        DiaChi = diaChi;
        QueQuan = queQuan;
    }

    public pesion(String ten, String soDT, String diaChi) {
        Ten = ten;
        SoDT = soDT;
        DiaChi = diaChi;
    }

    public String getId_Nguoi() {
        return id_Nguoi;
    }

    public void setId_Nguoi(String id_Nguoi) {
        this.id_Nguoi = id_Nguoi;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getQueQuan() {
        return QueQuan;
    }

    public void setQueQuan(String queQuan) {
        QueQuan = queQuan;
    }

    @Override
    public String toString() {
        return Ten;
    }
}
