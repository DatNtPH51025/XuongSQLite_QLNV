package com.example.xuongsql.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xuongsql.DAO.NhanVienDAO;
import com.example.xuongsql.DAO.PhongBanDAO;
import com.example.xuongsql.DTO.NhanVienDTO;
import com.example.xuongsql.DTO.PhongBanDTO;
import com.example.xuongsql.R;

import java.util.ArrayList;

public class NhanVienAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NhanVienDTO> listNhanVien;
    private NhanVienDAO nhanVienDAO;
    private PhongBanDAO phongBanDAO;
    private ArrayList<PhongBanDTO> listPhongBan;
    private int mapb;
    private SpinerAdapter spinnerAdapter;

    private PhongBanDTO phongBanDTO;

    public NhanVienAdapter(Context context, ArrayList<NhanVienDTO> listNhanVien) {
        this.context = context;
        this.listNhanVien = listNhanVien;
        nhanVienDAO = new NhanVienDAO(context);

    }

    @Override
    public int getCount() {
        return listNhanVien.size();
    }

    @Override
    public Object getItem(int position) {
        return listNhanVien.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("AdapterDebug", "getView được gọi cho vị trí: " + position);
        showNhanVien();
        View v = convertView;
        if (v == null) {
            v = View.inflate(context, R.layout.item_row_nv, null);
        }
        if (position >= listNhanVien.size()) {
            return v;
        }

        NhanVienDTO nv = listNhanVien.get(position);
        TextView txt_manv = v.findViewById(R.id.txt_manv);
        TextView txt_mapbnv = v.findViewById(R.id.txt_mapbnv);
        TextView txt_hodemnv = v.findViewById(R.id.txt_hodemnv);
        TextView txt_tennv = v.findViewById(R.id.txt_tennv);
        TextView txt_luongnv = v.findViewById(R.id.txt_luongnv);
        TextView txt_tuoinv = v.findViewById(R.id.txt_tuoinv);
        TextView txt_diachinv = v.findViewById(R.id.txt_diachinv);
        TextView txt_chucvunv = v.findViewById(R.id.txt_chucvunv);
        ImageButton btn_update_nv = v.findViewById(R.id.btn_update_nv);
        ImageButton btn_delete_nv = v.findViewById(R.id.btn_delete_nv);

        // Set text views
        txt_manv.setText("Mã nhân viên : " + nv.getMaNhanVien());
        txt_mapbnv.setText("Mã phòng ban :" + String.valueOf(nv.getMaPhongBan()));
        txt_hodemnv.setText("Họ đệm : " + nv.getHoDem());
        txt_tennv.setText("Tên : " + nv.getTen());
        txt_luongnv.setText("Luong : " + String.valueOf(nv.getLuong()));
        txt_tuoinv.setText("Tuoi : " + String.valueOf(nv.getTuoi()));
        txt_diachinv.setText("Dia chi :" + nv.getDiaChi());
        txt_chucvunv.setText("Chuc vu :" + nv.getChucVu());

        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("chucvu", "");
        Log.d("zzzzzzzzzz", "User Role: " + userRole);
        if (!userRole.equals("Admin")) {
            btn_update_nv.setVisibility(View.GONE);
            btn_delete_nv.setVisibility(View.GONE);
            txt_mapbnv.setVisibility(View.GONE);

        }

        btn_delete_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
                int check = nhanVienDAO.Delete(nv.getMaNhanVien());
                if (check < 0) {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                } else {
                    listNhanVien.clear();
                    listNhanVien = (ArrayList<NhanVienDTO>) nhanVienDAO.getAllNhanVien();
                    notifyDataSetChanged();
                }
            }
        });

        btn_update_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = View.inflate(context, R.layout.item_up_nv, null);
                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();

                EditText edt_manv_up = view1.findViewById(R.id.edt_manv_up);
                EditText edt_hoten = view1.findViewById(R.id.edt_hodemnv_up);
                EditText edt_ten = view1.findViewById(R.id.edt_tennv_up);
                EditText edt_luong = view1.findViewById(R.id.edt_luongnv_up);
                EditText edt_tuoi = view1.findViewById(R.id.edt_tuoinv_up);
                EditText edt_diachi = view1.findViewById(R.id.edt_diachinv_up);
                EditText edt_chucvu = view1.findViewById(R.id.edt_chucvunv_up);
                Spinner sp_phongban_add_up = view1.findViewById(R.id.sp_phongban_add_up);
                Button btn_update_nv_dialog = view1.findViewById(R.id.btn_update_nv_dialog);

                listPhongBan = new ArrayList<>();
                phongBanDAO = new PhongBanDAO(context);
                listPhongBan = (ArrayList<PhongBanDTO>) phongBanDAO.getAllPhongBan();
                spinnerAdapter = new SpinerAdapter(context, listPhongBan);
                sp_phongban_add_up.setAdapter(spinnerAdapter);

                sp_phongban_add_up.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mapb = listPhongBan.get(position).getMaPhongBan();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                for (int i = 0; i < listPhongBan.size(); i++) {
                    if (listPhongBan.get(i).getMaPhongBan() == nv.getMaPhongBan()) {
                        sp_phongban_add_up.setSelection(i);
                        break;
                    }
                }
                edt_manv_up.setText(nv.getMaNhanVien());
                edt_hoten.setText(nv.getHoDem());
                edt_ten.setText(nv.getTen());
                edt_luong.setText(String.valueOf(nv.getLuong()));
                edt_tuoi.setText(String.valueOf(nv.getTuoi()));
                edt_diachi.setText(nv.getDiaChi());
                edt_chucvu.setText(nv.getChucVu());

                btn_update_nv_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nhanVienDAO = new NhanVienDAO(context);
                        nv.setMaNhanVien(edt_manv_up.getText().toString().trim());
                        nv.setMaPhongBan(mapb);
                        nv.setHoDem(edt_hoten.getText().toString().trim());
                        nv.setTen(edt_ten.getText().toString().trim());
                        nv.setLuong(Integer.parseInt(edt_luong.getText().toString().trim()));
                        nv.setTuoi(Integer.parseInt(edt_tuoi.getText().toString().trim()));
                        nv.setDiaChi(edt_diachi.getText().toString().trim());
                        nv.setChucVu(edt_chucvu.getText().toString().trim());

                        int check = nhanVienDAO.UpdateNhanVien(nv);
                        if (check < 0) {
                            Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                        } else {
                            listNhanVien.clear();
                            listNhanVien = (ArrayList<NhanVienDTO>) nhanVienDAO.getAllNhanVien();
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        Log.d("AdapterDebug", "Tên hiển thị tại vị trí " + position + ": " + txt_tennv.getText().toString());
        return v;
    }

    public void updateNhanVienList(ArrayList<NhanVienDTO> newList) {
        this.listNhanVien = newList;
        Log.d("AdapterDebug", "Danh sách sau khi cập nhật: " + listNhanVien.toString());
        notifyDataSetChanged();
    }

    public void showNhanVien() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        int maphongban = sharedPreferences.getInt("maphongban", 0);
        if (maphongban == 0) {
            // If maphongban is 0, get all employees
            return;
        } else {
            // If maphongban has a specific value, filter employees by department
            ArrayList<NhanVienDTO> filteredNhanVienList = new ArrayList<>();
            for (NhanVienDTO nv : listNhanVien) {
                if (nv.getMaPhongBan() == maphongban) {
                    filteredNhanVienList.add(nv);
                }
            }
            listNhanVien = filteredNhanVienList;
        }
        notifyDataSetChanged();
    }

}