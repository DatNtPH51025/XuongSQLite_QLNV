package com.example.xuongsql.DbHepler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.xuongsql.DTO.NhanVienDTO;

//public class MyDbHelper extends SQLiteOpenHelper {
//
//    private static final String DB_NAME = "qlnv";
//    private static final int DB_VERSION = 5;
//
//
//    public MyDbHelper(@Nullable Context context) {
//        super(context, DB_NAME, null, DB_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//        String tb_Phongban = "CREATE TABLE PhongBan (\n" +
//                "    MaPhongBan       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//                "    TenPhongBan      TEXT    NOT NULL,\n" +
//                "    MoTa             TEXT,\n" +
//                "    SoLuongNhanVien  INTEGER DEFAULT 0\n" +
//                ");\n";
//        db.execSQL(tb_Phongban);
//        String tb_NhanVien = "CREATE TABLE NhanVien (\n" +
//                "    MaNhanVien TEXT PRIMARY KEY ,\n" +
//                "    MaPhongBan INTEGER REFERENCES PhongBan (MaPhongBan) DEFAULT 1,\n" +
//                "    HoDem      TEXT    NOT NULL,\n" +
//                "    Ten        TEXT    NOT NULL,\n" +
//                "    Luong      INTEGER DEFAULT 0," +
//                "    Tuoi       INTEGER DEFAULT 0,\n" +
//                "    DiaChi     TEXT    DEFAULT '',\n" +
//                "    ChucVu     TEXT    DEFAULT 'NhanVien'," +
//                "    MatKhau TEXT NOT NULL" +
//                ");\n";
//        db.execSQL(tb_NhanVien);
//        String insertpb = "INSERT INTO PhongBan VALUES (1,'Hành chính','Nhân viên hành chính',1)";
//        db.execSQL(insertpb);
//        String insertnv = "INSERT INTO NhanVien VALUES ('nv01',1,'Nguyễn Tiến ','Đạt',0,20,'Hà Nội','Admin','123')";
//        db.execSQL(insertnv);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (newVersion > oldVersion) {
//            db.execSQL("DROP TABLE IF EXISTS QuanLy");
//            db.execSQL("DROP TABLE IF EXISTS PhongBan");
//            db.execSQL("DROP TABLE IF EXISTS NhanVien");
//            onCreate(db);
//        }
//
//    }
//}

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "qlnv";
    private static final int DB_VERSION = 5;

    public MyDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng PhongBan
        String tb_Phongban = "CREATE TABLE PhongBan (\n" +
                "    MaPhongBan       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    TenPhongBan      TEXT    NOT NULL,\n" +
                "    MoTa             TEXT,\n" +
                "    SoLuongNhanVien  INTEGER DEFAULT 0\n" +
                ");\n";
        db.execSQL(tb_Phongban);

        // Tạo bảng NhanVien
        String tb_NhanVien = "CREATE TABLE NhanVien (\n" +
                "    MaNhanVien TEXT PRIMARY KEY ,\n" +
                "    MaPhongBan INTEGER REFERENCES PhongBan (MaPhongBan) DEFAULT 1,\n" +
                "    HoDem      TEXT    NOT NULL,\n" +
                "    Ten        TEXT    NOT NULL,\n" +
                "    Luong      INTEGER DEFAULT 0,\n" +
                "    Tuoi       INTEGER DEFAULT 0,\n" +
                "    DiaChi     TEXT    DEFAULT '',\n" +
                "    ChucVu     TEXT    DEFAULT 'NhanVien',\n" +
                "    MatKhau    TEXT    NOT NULL\n" +
                ");\n";
        db.execSQL(tb_NhanVien);

        // Tạo trigger để cập nhật số lượng nhân viên trong bảng PhongBan
        String trigger_ThemNhanVien = "CREATE TRIGGER IF NOT EXISTS UpdateSoLuongNhanVienAfterInsert\n" +
                "AFTER INSERT ON NhanVien\n" +
                "FOR EACH ROW\n" +
                "BEGIN\n" +
                "    UPDATE PhongBan\n" +
                "    SET SoLuongNhanVien = SoLuongNhanVien + 1\n" +
                "    WHERE MaPhongBan = NEW.MaPhongBan;\n" +
                "END;\n";
        db.execSQL(trigger_ThemNhanVien);

        String trigger_XoaNhanVien =
                "CREATE TRIGGER IF NOT EXISTS UpdateSoLuongNhanVienAfterDelete " +
                        "AFTER DELETE ON NhanVien " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "   UPDATE PhongBan " +
                        "   SET SoLuongNhanVien = SoLuongNhanVien - 1 " +
                        "   WHERE MaPhongBan = OLD.MaPhongBan AND SoLuongNhanVien > 0; " +
                        "END;";
        db.execSQL(trigger_XoaNhanVien);


        String trigger_UpdateNhanVien =
                "CREATE TRIGGER IF NOT EXISTS UpdateSoLuongNhanVienAfterUpdate " +
                        "AFTER UPDATE OF MaPhongBan ON NhanVien " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "   UPDATE PhongBan " +
                        "   SET SoLuongNhanVien = SoLuongNhanVien - 1 " +
                        "   WHERE MaPhongBan = OLD.MaPhongBan AND SoLuongNhanVien > 0; " +
                        "   " +
                        "   UPDATE PhongBan " +
                        "   SET SoLuongNhanVien = SoLuongNhanVien + 1 " +
                        "   WHERE MaPhongBan = NEW.MaPhongBan; " +
                        "END;";
        db.execSQL(trigger_UpdateNhanVien);

        // Chèn dữ liệu mẫu vào bảng PhongBan
        String insertpb = "INSERT INTO PhongBan VALUES (1,'Hành chính','Nhân viên hành chính',0)";
        db.execSQL(insertpb);

        // Chèn dữ liệu mẫu vào bảng NhanVien
        String insertnv = "INSERT INTO NhanVien VALUES ('nv01',1,'Nguyễn Tiến ','Đạt',0,20,'Hà Nội','Admin','123')";
        db.execSQL(insertnv);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS PhongBan");
            db.execSQL("DROP TABLE IF EXISTS NhanVien");
            onCreate(db);
        }
    }
    public String layTenNguoiDung(String maNhanVien) {
        String tenNguoiDung = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Ten FROM NhanVien WHERE MaNhanVien = ?", new String[]{maNhanVien});

        if (cursor.moveToFirst()) {
            tenNguoiDung = cursor.getString(0);  // Lấy giá trị cột "Ten"
        }
        cursor.close();
        db.close();
        return tenNguoiDung;
    }



}

