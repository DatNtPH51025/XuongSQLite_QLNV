package com.example.xuongsql.DTO;

public class NhanVienDTO {
    private String MaNhanVien;
    private int MaPhongBan;
    private String HoDem;
    private String Ten;
    private int Luong;
    private int Tuoi;
    private String DiaChi;
    private String ChucVu;
    private String matKhau;

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NhanVienDTO() {
    }

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        MaNhanVien = maNhanVien;
    }

    public int getMaPhongBan() {
        return MaPhongBan;
    }

    public void setMaPhongBan(int maPhongBan) {
        MaPhongBan = maPhongBan;
    }

    public String getHoDem() {
        return HoDem;
    }

    public void setHoDem(String hoDem) {
        HoDem = hoDem;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public int getLuong() {
        return Luong;
    }

    public void setLuong(int luong) {
        Luong = luong;
    }

    public int getTuoi() {
        return Tuoi;
    }

    public void setTuoi(int tuoi) {
        Tuoi = tuoi;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String chucVu) {
        ChucVu = chucVu;
    }
}
