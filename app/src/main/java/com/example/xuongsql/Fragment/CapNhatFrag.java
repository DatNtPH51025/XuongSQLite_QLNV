package com.example.xuongsql.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.xuongsql.Adapter.SpinerAdapter;
import com.example.xuongsql.DAO.NhanVienDAO;
import com.example.xuongsql.DAO.PhongBanDAO;
import com.example.xuongsql.DTO.NhanVienDTO;
import com.example.xuongsql.DTO.PhongBanDTO;
import com.example.xuongsql.MainActivity;
import com.example.xuongsql.R;

import java.util.ArrayList;

public class CapNhatFrag extends Fragment {
    PhongBanDAO phongBanDAO;
    ArrayList<PhongBanDTO> listPhongBan;
    int mapb;
    SpinerAdapter spinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.capnhat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText edt_manv_cn = view.findViewById(R.id.edt_manv_cn);
        EditText edt_hodemnv_cn = view.findViewById(R.id.edt_hodemnv_cn);
        EditText edt_tennv_cn = view.findViewById(R.id.edt_tennv_cn);
        EditText edt_tuoinv_cn = view.findViewById(R.id.edt_tuoinv_cn);
        EditText edt_diachinv_cn = view.findViewById(R.id.edt_diachinv_cn);
        EditText edt_chucvunv_cn = view.findViewById(R.id.edt_chucvunv_cn);
        Spinner sp_phongban_add_cn = view.findViewById(R.id.sp_phongban_add_cn);
        Button btn_update_nv_dialog = view.findViewById(R.id.btn_update_nv_dialog);

        SharedPreferences sha = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String manhanvien = sha.getString("manhanvien", "");
        String hotenvn = sha.getString("hodem", "");
        String tennv = sha.getString("ten", "");
        int tuoinv = sha.getInt("tuoi", 0);
        String diachinv = sha.getString("diachi", "");
        String chucvunv = sha.getString("chucvu", "");
        Log.d("zzzzzzzzz", hotenvn);

        listPhongBan = new ArrayList<>();
        phongBanDAO = new PhongBanDAO(getContext());
        listPhongBan = (ArrayList<PhongBanDTO>) phongBanDAO.getAllPhongBan();
        spinnerAdapter = new SpinerAdapter(getContext(), listPhongBan);
        sp_phongban_add_cn.setAdapter(spinnerAdapter);
        for (int i = 0; i < listPhongBan.size(); i++) {
            if (listPhongBan.get(i).getMaPhongBan() == mapb) {
                sp_phongban_add_cn.setSelection(i);
                break;
            }
            sp_phongban_add_cn.setSelection(i);
        }


        edt_manv_cn.setText(manhanvien);
        edt_hodemnv_cn.setText(hotenvn);
        edt_tennv_cn.setText(tennv);
        edt_tuoinv_cn.setText(String.valueOf(tuoinv));
        edt_diachinv_cn.setText(diachinv);
        edt_chucvunv_cn.setText(chucvunv);



        btn_update_nv_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các ô nhập liệu
                String manv = edt_manv_cn.getText().toString().trim();
                String hoten = edt_hodemnv_cn.getText().toString().trim();
                String ten = edt_tennv_cn.getText().toString().trim();
                String tuoiStr = edt_tuoinv_cn.getText().toString().trim();
                String diachi = edt_diachinv_cn.getText().toString().trim();
                String chucvu = edt_chucvunv_cn.getText().toString().trim();
                int maphongban = listPhongBan.get(sp_phongban_add_cn.getSelectedItemPosition()).getMaPhongBan();

                // Kiểm tra nếu các trường trống
                if (manv.isEmpty() || hoten.isEmpty() || ten.isEmpty() || tuoiStr.isEmpty() || diachi.isEmpty() || chucvu.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                int tuoi = Integer.parseInt(tuoiStr);

                // Kiểm tra tuổi phải lớn hơn 18
                if (tuoi < 18) {
                    Toast.makeText(getContext(), "Tuổi phải lớn hơn 18", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng NhanVienDTO với thông tin cập nhật
                NhanVienDTO nhanVienDTO = new NhanVienDTO();
                nhanVienDTO.setMaNhanVien(manv);
                nhanVienDTO.setHoDem(hoten);
                nhanVienDTO.setTen(ten);
                nhanVienDTO.setTuoi(tuoi);
                nhanVienDTO.setDiaChi(diachi);
                nhanVienDTO.setChucVu(chucvu);
                nhanVienDTO.setMaPhongBan(maphongban);

                // Cập nhật dữ liệu
                NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
                int result = nhanVienDAO.UpdateNhanVien(nhanVienDTO);

                // Kiểm tra kết quả cập nhật
                if (result > 0) {
                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                    // Cập nhật cờ isInfoUpdated trong SharedPreferences
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", MODE_PRIVATE).edit();
                    editor.putBoolean("isInfoUpdated", true);
                    editor.apply();

                    // Gọi phương thức onInfoUpdated trong MainActivity để kích hoạt các menu khác
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).onInfoUpdated();
                    }

                } else {
                    Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
