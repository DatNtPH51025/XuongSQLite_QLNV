package com.example.xuongsql.DTO;

public class PhongBanDTO {
    int maPhongBan;
    String tenPhongBan;
    String moTaPhongBan;
    int soLuongNhanVien;

    public int getMaPhongBan() {
        return maPhongBan;
    }

    public void setMaPhongBan(int maPhongBan) {
        this.maPhongBan = maPhongBan;
    }

    public String getTenPhongBan() {return tenPhongBan;}

    public void setTenPhongBan(String tenPhongBan) {
        this.tenPhongBan = tenPhongBan;
    }

    public String getMoTaPhongBan() {return moTaPhongBan;}

    public void setMoTaPhongBan(String moTaPhongBan) {this.moTaPhongBan = moTaPhongBan;}

    public int getSoLuongNhanVien(){return soLuongNhanVien; }

    public void setSoLuongNhanVien(int soLuongNhanVien) {this.soLuongNhanVien = soLuongNhanVien;}

}
