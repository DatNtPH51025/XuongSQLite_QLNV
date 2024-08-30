package com.example.xuongsql.Fragment;

//import android.app.AlertDialog;
//import android.content.ContentQueryMap;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Spinner;
//import android.widget.SpinnerAdapter;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.widget.SearchView;
//import androidx.fragment.app.Fragment;
//
//import com.example.xuongsql.Adapter.NhanVienAdapter;
//import com.example.xuongsql.Adapter.SpinerAdapter;
//import com.example.xuongsql.DAO.NhanVienDAO;
//import com.example.xuongsql.DAO.PhongBanDAO;
//import com.example.xuongsql.DTO.NhanVienDTO;
//import com.example.xuongsql.DTO.PhongBanDTO;
//import com.example.xuongsql.R;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//
//public class QuanLyThanhVienFrag extends Fragment {
//    ListView lv_nv;
//    NhanVienDAO nhanVienDAO;
//    ArrayList<NhanVienDTO> listnv;
//    NhanVienAdapter nhanVienAdapter;
//    Spinner sp_phongban;
//    ArrayList<PhongBanDTO> listphongban;
//    PhongBanDAO phongBanDAO;
//    SpinnerAdapter spinnerAdapter;
//    FloatingActionButton btn_addnv;
//    int mapb;
//    String userRole;
//    private SearchView searchView;
//    Button btn_xx;
//    boolean isAscending = true;
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_qanlynhanvien, container, false);
//
//        lv_nv = v.findViewById(R.id.lv_tv);
//        btn_addnv = v.findViewById(R.id.btn_add_nhanvien);
//        searchView = v.findViewById(R.id.search_nhanvien);
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
//        userRole = sharedPreferences.getString("chucvu", "");
//        if (!userRole.equals("Admin")) {
//            btn_addnv.setVisibility(View.GONE);
//        }
//
//        return v;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        showNhanVien();
//        Log.d("zzzzzzzzzz", "Co chay");
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        nhanVienDAO = new NhanVienDAO(getContext());
//        listnv = nhanVienDAO.getAllNhanVien();
//        nhanVienAdapter = new NhanVienAdapter(getContext(), listnv); // Pass userRole to the adapter
//        lv_nv.setAdapter(nhanVienAdapter);
//        btn_xx = view.findViewById(R.id.btn_sort);
//
//
//        // Retrieve the selected department ID from SharedPreferences
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
//        int selectedPhongBanId = sharedPreferences.getInt("maphongban", -1);
//
//        if (selectedPhongBanId != -1) {
//            // Filter employees based on the selected department
//            listnv = nhanVienDAO.getNhanVienByPhongBan(selectedPhongBanId);
//        } else {
//            // Load all employees if no department is selected
//            listnv = nhanVienDAO.getAllNhanVien();
//        }
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                ArrayList<NhanVienDTO> searchList = nhanVienDAO.searchNhanVienByTen(newText);
//                nhanVienAdapter = new NhanVienAdapter(getContext(), searchList);
//                lv_nv.setAdapter(nhanVienAdapter);
//                return true;
//            }
//        });
//
//        btn_addnv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                View view1 = inflater.inflate(R.layout.item_add_nv, null);
//                builder.setView(view1);
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//                sp_phongban = view1.findViewById(R.id.sp_phongban_add);
//                EditText edt_manv = view1.findViewById(R.id.edt_manv);
//                EditText edt_hodemnv = view1.findViewById(R.id.edt_hodemnv);
//                EditText edt_tennv = view1.findViewById(R.id.edt_tennv);
//                EditText edt_luongnv = view1.findViewById(R.id.edt_luongnv);
//                EditText edt_tuoinv = view1.findViewById(R.id.edt_tuoinv);
//                EditText edt_diachinv = view1.findViewById(R.id.edt_diachinv);
//                EditText edt_chucvunv = view1.findViewById(R.id.edt_chucvunv);
//                EditText edt_matkhaunv = view1.findViewById(R.id.edt_matkhaunv);
//                Button btn_them = view1.findViewById(R.id.btn_themnv);
//
//                listphongban = new ArrayList<>();
//                phongBanDAO = new PhongBanDAO(getContext());
//                listphongban = (ArrayList<PhongBanDTO>) phongBanDAO.getAllPhongBan();
//                spinnerAdapter = new SpinerAdapter(getContext(), listphongban);
//                sp_phongban.setAdapter(spinnerAdapter);
//                sp_phongban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        mapb = listphongban.get(position).getMaPhongBan();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//
//                btn_them.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String manv = edt_manv.getText().toString();
//                        String hodemnv = edt_hodemnv.getText().toString();
//                        String tennv = edt_tennv.getText().toString();
//                        String luong = edt_luongnv.getText().toString();
//                        String tuoinv = edt_tuoinv.getText().toString();
//                        String diachinv = edt_diachinv.getText().toString();
//                        String chucvunv = edt_chucvunv.getText().toString();
//                        String matkhau = edt_matkhaunv.getText().toString();
//
//                        if (manv.isEmpty() || hodemnv.isEmpty() || tennv.isEmpty() || luong.isEmpty() || tuoinv.isEmpty() || diachinv.isEmpty() || chucvunv.isEmpty()) {
//                            Toast.makeText(getContext(), "Bạn cần nhập đủ thông tin", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        int luongValue;
//                        int tuoiValue;
//                        try {
//                            luongValue = Integer.parseInt(luong);
//                        } catch (NumberFormatException e) {
//                            Toast.makeText(getContext(), "Lương phải là một số nguyên hợp lệ", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        try {
//                            tuoiValue = Integer.parseInt(tuoinv);
//                        } catch (NumberFormatException e) {
//                            Toast.makeText(getContext(), "Tuổi phải là một số nguyên hợp lệ", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        if (luongValue < 0) {
//                            Toast.makeText(getContext(), "Lương phải lớn hơn hoặc bằng 0", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        if (tuoiValue < 18 || tuoiValue > 65) {
//                            Toast.makeText(getContext(), "Tuổi phải nằm trong khoảng từ 18 đến 65", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        NhanVienDTO nhanVienDTO = new NhanVienDTO();
//                        nhanVienDTO.setMaNhanVien(manv);
//                        nhanVienDTO.setMaPhongBan(mapb);
//                        nhanVienDTO.setHoDem(hodemnv);
//                        nhanVienDTO.setTen(tennv);
//                        nhanVienDTO.setLuong(luongValue);
//                        nhanVienDTO.setTuoi(tuoiValue);
//                        nhanVienDTO.setDiaChi(diachinv);
//                        nhanVienDTO.setChucVu(chucvunv);
//                        nhanVienDTO.setMatKhau(matkhau);
//                        int kq = nhanVienDAO.DangKyNhanVien(nhanVienDTO);
//                        if (kq > 0) {
//                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
//                            listnv = (ArrayList<NhanVienDTO>) nhanVienDAO.getAllNhanVien();
//                            nhanVienAdapter = new NhanVienAdapter(getContext(), listnv); // Pass userRole to the adapter
//                            lv_nv.setAdapter(nhanVienAdapter);
//                            dialog.dismiss();
//                        } else {
//                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                });
//            }
//        });
//
//        btn_xx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listnv = nhanVienDAO.getAllNhanVienSortedByName(isAscending);
//                isAscending = !isAscending;
//                nhanVienAdapter = new NhanVienAdapter(getContext(), listnv);
//                lv_nv.setAdapter(nhanVienAdapter);
//                btn_xx.setText(isAscending ? "Sắp xếp theo tên (A-Z)" : "Sắp xếp theo tên (Z-A)");
//
//
//            }
//        });
//
//    }
//    private void showNhanVien() {
//        listnv.clear();
//        listnv.addAll(nhanVienDAO.getAllNhanVien());
//        if (nhanVienAdapter != null) {
//            nhanVienAdapter.notifyDataSetChanged();
//        }
//    }
//
//}

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.xuongsql.Adapter.NhanVienAdapter;
import com.example.xuongsql.Adapter.SpinerAdapter;
import com.example.xuongsql.DAO.NhanVienDAO;
import com.example.xuongsql.DAO.PhongBanDAO;
import com.example.xuongsql.DTO.NhanVienDTO;
import com.example.xuongsql.DTO.PhongBanDTO;
import com.example.xuongsql.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QuanLyThanhVienFrag extends Fragment {
    ListView lv_nv;
    NhanVienDAO nhanVienDAO;
    ArrayList<NhanVienDTO> listnv;
    NhanVienAdapter nhanVienAdapter;
    Spinner sp_phongban;
    ArrayList<PhongBanDTO> listphongban;
    PhongBanDAO phongBanDAO;
    SpinnerAdapter spinnerAdapter;
    FloatingActionButton btn_addnv;
    Button btn_xx;
    int mapb;
    String userRole;
    private SearchView searchView;
    boolean isAscending = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quanlynhanvien, container, false);

        lv_nv = v.findViewById(R.id.lv_tv);
        btn_addnv = v.findViewById(R.id.btn_add_nhanvien);
        searchView = v.findViewById(R.id.search_nhanvien);
        btn_xx = v.findViewById(R.id.btn_sort);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        userRole = sharedPreferences.getString("chucvu", "");
        if (!userRole.equals("Admin")) {
            btn_addnv.setVisibility(View.GONE);
        }

        nhanVienDAO = new NhanVienDAO(getContext());
        listnv = nhanVienDAO.getAllNhanVien();
        nhanVienAdapter = new NhanVienAdapter(getContext(), listnv);
        lv_nv.setAdapter(nhanVienAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<NhanVienDTO> searchList = nhanVienDAO.searchNhanVienByTen(newText);
                nhanVienAdapter.updateNhanVienList(searchList);
                return true;
            }
        });

        btn_addnv.setOnClickListener(v1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater1 = LayoutInflater.from(getContext());
            View view1 = inflater1.inflate(R.layout.item_add_nv, null);
            builder.setView(view1);
            AlertDialog dialog = builder.create();
            dialog.show();

            sp_phongban = view1.findViewById(R.id.sp_phongban_add);
            EditText edt_manv = view1.findViewById(R.id.edt_manv);
            EditText edt_hodemnv = view1.findViewById(R.id.edt_hodemnv);
            EditText edt_tennv = view1.findViewById(R.id.edt_tennv);
            EditText edt_luongnv = view1.findViewById(R.id.edt_luongnv);
            EditText edt_tuoinv = view1.findViewById(R.id.edt_tuoinv);
            EditText edt_diachinv = view1.findViewById(R.id.edt_diachinv);
            EditText edt_chucvunv = view1.findViewById(R.id.edt_chucvunv);
            EditText edt_matkhaunv = view1.findViewById(R.id.edt_matkhaunv);
            Button btn_them = view1.findViewById(R.id.btn_themnv);

            listphongban = new ArrayList<>();
            phongBanDAO = new PhongBanDAO(getContext());
            listphongban = (ArrayList<PhongBanDTO>) phongBanDAO.getAllPhongBan();
            spinnerAdapter = new SpinerAdapter(getContext(), listphongban);
            sp_phongban.setAdapter(spinnerAdapter);
            sp_phongban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mapb = listphongban.get(position).getMaPhongBan();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            btn_them.setOnClickListener(v2 -> {
                String manv = edt_manv.getText().toString();
                String hodemnv = edt_hodemnv.getText().toString();
                String tennv = edt_tennv.getText().toString();
                String luong = edt_luongnv.getText().toString();
                String tuoinv = edt_tuoinv.getText().toString();
                String diachinv = edt_diachinv.getText().toString();
                String chucvunv = edt_chucvunv.getText().toString();
                String matkhau = edt_matkhaunv.getText().toString();

                if (manv.isEmpty() || hodemnv.isEmpty() || tennv.isEmpty() || luong.isEmpty() || tuoinv.isEmpty() || diachinv.isEmpty() || chucvunv.isEmpty()) {
                    Toast.makeText(getContext(), "Bạn cần nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                int luongValue;
                int tuoiValue;
                try {
                    luongValue = Integer.parseInt(luong);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Lương phải là một số nguyên hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    tuoiValue = Integer.parseInt(tuoinv);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Tuổi phải là một số nguyên hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (luongValue < 0) {
                    Toast.makeText(getContext(), "Lương phải lớn hơn hoặc bằng 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tuoiValue < 18 || tuoiValue > 65) {
                    Toast.makeText(getContext(), "Tuổi phải nằm trong khoảng từ 18 đến 65", Toast.LENGTH_SHORT).show();
                    return;
                }

                NhanVienDTO nhanVienDTO = new NhanVienDTO();
                nhanVienDTO.setMaNhanVien(manv);
                nhanVienDTO.setMaPhongBan(mapb);
                nhanVienDTO.setHoDem(hodemnv);
                nhanVienDTO.setTen(tennv);
                nhanVienDTO.setLuong(luongValue);
                nhanVienDTO.setTuoi(tuoiValue);
                nhanVienDTO.setDiaChi(diachinv);
                nhanVienDTO.setChucVu(chucvunv);
                nhanVienDTO.setMatKhau(matkhau);
                int kq = nhanVienDAO.DangKyNhanVien(nhanVienDTO);
                if (kq > 0) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    listnv = (ArrayList<NhanVienDTO>) nhanVienDAO.getAllNhanVien();
                    nhanVienAdapter.updateNhanVienList(listnv);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btn_xx.setOnClickListener(v1 -> {
            if (listnv == null || listnv.isEmpty()) {
                Toast.makeText(getContext(), "Danh sách nhân viên trống", Toast.LENGTH_SHORT).show();
                return;
            }

            // Sắp xếp danh sách theo tên
            Collections.sort(listnv, new Comparator<NhanVienDTO>() {
                @Override
                public int compare(NhanVienDTO nv1, NhanVienDTO nv2) {
                    String ten1 = nv1.getTen() != null ? nv1.getTen() : "";
                    String ten2 = nv2.getTen() != null ? nv2.getTen() : "";

                    if (isAscending) {
                        return ten1.compareToIgnoreCase(ten2);
                    } else {
                        return ten2.compareToIgnoreCase(ten1);
                    }
                }
            });

            // Cập nhật danh sách trong adapter
            nhanVienAdapter.updateNhanVienList(listnv);

            // Đổi trạng thái sắp xếp
            isAscending = !isAscending;

            // Cập nhật text của nút sắp xếp
            btn_xx.setText(isAscending ? "Sắp xếp theo tên (A-Z)" : "Sắp xếp theo tên (Z-A)");

            // Debug log để kiểm tra
            for (NhanVienDTO nv : listnv) {
                Log.d("SortDebug", "Tên nhân viên: " + nv.getTen());
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        showNhanVien();
        Log.d("zzzzzzzzzz", "Co chay");
    }

    private void showNhanVien() {
        listnv.clear();
        listnv.addAll(nhanVienDAO.getAllNhanVien());
        if (nhanVienAdapter != null) {
            nhanVienAdapter.notifyDataSetChanged();
        }
    }
}
