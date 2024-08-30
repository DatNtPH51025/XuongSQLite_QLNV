package com.example.xuongsql;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.xuongsql.DAO.NhanVienDAO;
import com.example.xuongsql.DTO.NhanVienDTO;

public class DangNhapActivity extends AppCompatActivity {

    EditText edt_manv_dn, edt_matkhau_dn;
    Button btn_dangnhap;
    Button btn_dangky;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);

        nhanVienDAO = new NhanVienDAO(this);
        edt_manv_dn = findViewById(R.id.edt_manv_dn);
        edt_matkhau_dn = findViewById(R.id.edt_matkhau_dn);
        btn_dangnhap = findViewById(R.id.btn_dangnhap);
        btn_dangky = findViewById(R.id.btn_dangky_dn);
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String manv = edt_manv_dn.getText().toString();
                String matkhau = edt_matkhau_dn.getText().toString();
                if (TextUtils.isEmpty(manv) || TextUtils.isEmpty(matkhau)) {
                    Toast.makeText(DangNhapActivity.this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                NhanVienDTO check = nhanVienDAO.DangNhapNhanVien(manv, matkhau);
                if (check == null) {
                    Toast.makeText(DangNhapActivity.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(DangNhapActivity.this, MainActivity.class));
                    Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class));
            }
        });


    }
}