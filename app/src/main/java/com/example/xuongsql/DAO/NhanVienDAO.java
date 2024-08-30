package com.example.xuongsql.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.xuongsql.DTO.NhanVienDTO;
import com.example.xuongsql.DbHepler.MyDbHelper;

import java.util.ArrayList;

public class NhanVienDAO {
    MyDbHelper myDbHelper;
    SQLiteDatabase db;
    Context context;
    SharedPreferences sharedPreferences;

    public NhanVienDAO(Context context) {
        this.context = context;
        myDbHelper = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();
        sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    public int DangKyNhanVien(NhanVienDTO nhanVienDTO) {
        ContentValues v = new ContentValues();
        v.put("MaNhanVien", nhanVienDTO.getMaNhanVien());
        v.put("MaPhongBan", nhanVienDTO.getMaPhongBan());
        v.put("HoDem", nhanVienDTO.getHoDem());
        v.put("Ten", nhanVienDTO.getTen());
        v.put("Luong", nhanVienDTO.getLuong());
        v.put("Tuoi", nhanVienDTO.getTuoi());
        v.put("DiaChi", nhanVienDTO.getDiaChi());
        v.put("ChucVu", nhanVienDTO.getChucVu());
        v.put("MatKhau", nhanVienDTO.getMatKhau());

        long check = db.insert("NhanVien", null, v);
        return (int) check;
    }

    public NhanVienDTO DangNhapNhanVien(String tendn, String matkhau) {
        NhanVienDTO nhanVienDTO = null;
        String[] args = {tendn, matkhau};
        Cursor cursor = db.rawQuery("SELECT * FROM NhanVien WHERE manhanvien=? AND matkhau=?", args);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("manhanvien", cursor.getString(0));
            editor.putString("hodem", cursor.getString(2));
            editor.putString("ten", cursor.getString(3));
            editor.putInt("tuoi", cursor.getInt(5));
            editor.putString("diachi", cursor.getString(6));
            editor.putInt("luong", cursor.getInt(4));
            editor.putString("matkhau", cursor.getString(8));
            editor.putString("chucvu", cursor.getString(7));
            editor.commit();
            nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setMaNhanVien(cursor.getString(0));
            nhanVienDTO.setMaPhongBan(cursor.getInt(1));
            nhanVienDTO.setHoDem(cursor.getString(2));
            nhanVienDTO.setTen(cursor.getString(3));
            nhanVienDTO.setLuong(cursor.getInt(4));
            nhanVienDTO.setTuoi(cursor.getInt(5));
            nhanVienDTO.setDiaChi(cursor.getString(6));
            nhanVienDTO.setChucVu(cursor.getString(7));
            nhanVienDTO.setMatKhau(cursor.getString(8));
            cursor.close();

        }
        return nhanVienDTO;
    }

    public ArrayList<NhanVienDTO> getAllNhanVien() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM NhanVien", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNhanVien(cursor.getString(0));
                nv.setMaPhongBan(cursor.getInt(1));
                nv.setHoDem(cursor.getString(2));
                nv.setTen(cursor.getString(3));
                nv.setLuong(cursor.getInt(4));
                nv.setTuoi(cursor.getInt(5));
                nv.setDiaChi(cursor.getString(6));
                nv.setChucVu(cursor.getString(7));
                list.add(nv);
            } while (cursor.moveToNext());


        }
        return list;

    }
    public int Delete(String id) {
        int result = db.delete("NhanVien", "MaNhanVien=?", new String[]{String.valueOf(id)});
        return result;
    }
    public int UpdateNhanVien(NhanVienDTO nhanVienDTO) {
        ContentValues v = new ContentValues();
        v.put("MaNhanVien", nhanVienDTO.getMaNhanVien());
        v.put("MaPhongBan", nhanVienDTO.getMaPhongBan());
        v.put("HoDem", nhanVienDTO.getHoDem());
        v.put("Ten", nhanVienDTO.getTen());
        v.put("Luong", nhanVienDTO.getLuong());
        v.put("Tuoi", nhanVienDTO.getTuoi());
        v.put("DiaChi", nhanVienDTO.getDiaChi());
        v.put("ChucVu", nhanVienDTO.getChucVu());

        int result = db.update("NhanVien", v, "MaNhanVien=?", new String[]{String.valueOf(nhanVienDTO.getMaNhanVien())});
        return result;

    }
    public ArrayList<NhanVienDTO> searchNhanVienByTen(String ten) {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        String query = "SELECT * FROM NhanVien WHERE Ten LIKE ?";
        String wildcardKeyword = "%" + ten + "%";
        Cursor cursor = db.rawQuery(query, new String[]{wildcardKeyword});

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNhanVien(cursor.getString(0));
                nv.setMaPhongBan(cursor.getInt(1));
                nv.setHoDem(cursor.getString(2));
                nv.setTen(cursor.getString(3));
                nv.setLuong(cursor.getInt(4));
                nv.setTuoi(cursor.getInt(5));
                nv.setDiaChi(cursor.getString(6));
                nv.setChucVu(cursor.getString(7));
                list.add(nv);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return list;
    }
    public ArrayList<NhanVienDTO> getAllNhanVienSortedByName(boolean isAscending) {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        String sortOrder = isAscending ? "ASC" : "DESC";
        String query = "SELECT * FROM NhanVien ORDER BY Ten " + sortOrder;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNhanVien(cursor.getString(0));
                nv.setMaPhongBan(cursor.getInt(1));
                nv.setHoDem(cursor.getString(2));
                nv.setTen(cursor.getString(3));
                nv.setLuong(cursor.getInt(4));
                nv.setTuoi(cursor.getInt(5));
                nv.setDiaChi(cursor.getString(6));
                nv.setChucVu(cursor.getString(7));
                list.add(nv);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public ArrayList<NhanVienDTO> getNhanVienByPhongBan(int maPhongBan) {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM NhanVien WHERE MaPhongBan = ?", new String[]{String.valueOf(maPhongBan)});
        if (cursor.moveToFirst()) {
            do {
                NhanVienDTO nhanVien = new NhanVienDTO();
                nhanVien.setMaNhanVien(cursor.getString(0));
                nhanVien.setMaPhongBan(cursor.getInt(1));
                nhanVien.setHoDem(cursor.getString(2));
                nhanVien.setTen(cursor.getString(3));
                nhanVien.setLuong(cursor.getInt(4));
                nhanVien.setTuoi(cursor.getInt(5));
                nhanVien.setDiaChi(cursor.getString(6));
                nhanVien.setChucVu(cursor.getString(7));
                list.add(nhanVien);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


}
