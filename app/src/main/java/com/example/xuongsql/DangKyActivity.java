package com.example.xuongsql;

import android.content.SharedPreferences;
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

import java.util.ArrayList;

public class DangKyActivity extends AppCompatActivity {

    private EditText edt_tendn, edt_hodem, edt_ten, edt_matkhau, edt_confrim;
    private Button btn_dangky;
    private ArrayList<NhanVienDTO> list;
    private NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);

        edt_tendn = findViewById(R.id.edt_manv);
        edt_hodem = findViewById(R.id.edt_hodemnv);
        edt_ten = findViewById(R.id.edt_tennv);
        edt_matkhau = findViewById(R.id.edt_matkhau);
        edt_confrim = findViewById(R.id.edt_confrim);
        btn_dangky = findViewById(R.id.btn_dangky);
        list = new ArrayList<>();
        nhanVienDAO = new NhanVienDAO(this);

        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tendn = edt_tendn.getText().toString().trim();
                String matkhau = edt_matkhau.getText().toString().trim();
                String hodem = edt_hodem.getText().toString().trim();
                String tennv = edt_ten.getText().toString().trim();
                String confrim = edt_confrim.getText().toString().trim();

                if (TextUtils.isEmpty(tendn) || TextUtils.isEmpty(matkhau) || TextUtils.isEmpty(confrim)) {
                    Toast.makeText(DangKyActivity.this, "Bạn cần nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!matkhau.equals(confrim)) {
                    Toast.makeText(DangKyActivity.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                    return;
                }

                NhanVienDTO nhanVienDTO = new NhanVienDTO();
                nhanVienDTO.setMaNhanVien(tendn);
                nhanVienDTO.setHoDem(hodem);
                nhanVienDTO.setTen(tennv);
                nhanVienDTO.setMatKhau(matkhau);
                int check = nhanVienDAO.DangKyNhanVien(nhanVienDTO);

                if (check > 0) {
                    list.add(nhanVienDTO);
                    Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                    editor.putBoolean("isInfoUpdated", false); // Set this to false for new users
                    editor.apply();
                    finish();
                } else {
                    Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
