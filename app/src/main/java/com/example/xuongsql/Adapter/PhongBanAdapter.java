package com.example.xuongsql.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuongsql.DAO.PhongBanDAO;
import com.example.xuongsql.DTO.PhongBanDTO;
import com.example.xuongsql.Fragment.QuanLyThanhVienFrag;
import com.example.xuongsql.MainActivity;
import com.example.xuongsql.R;

import java.util.ArrayList;

public class PhongBanAdapter extends RecyclerView.Adapter<PhongBanAdapter.PhongBanHolder> {
    private Context context;
    private ArrayList<PhongBanDTO> listPhongBan;
    private PhongBanDAO phongBanDAO;

    public PhongBanAdapter(Context context, ArrayList<PhongBanDTO> listPhongBan) {
        this.context = context;
        this.listPhongBan = listPhongBan;
    }

    @NonNull
    @Override
    public PhongBanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_row_phongban, parent, false);

        return new PhongBanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongBanHolder holder, @SuppressLint("RecyclerView") int position) {
        PhongBanDTO phongBanDTO = listPhongBan.get(position);
        holder.txt_maphongban.setText("Mã phòng ban: " + phongBanDTO.getMaPhongBan() + "");
        holder.txt_tenphongban.setText("Tên phòng ban: " + phongBanDTO.getTenPhongBan());
        holder.txt_mota.setText("Mô tả: " + phongBanDTO.getMoTaPhongBan());
        holder.txt_soluongnhanvien.setText("Số lượng nhân viên: " + phongBanDTO.getSoLuongNhanVien() + "");

        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("chucvu", "");

        if (!userRole.equals("Admin")) {
            holder.btn_update.setVisibility(View.GONE);
            holder.btn_delete.setVisibility(View.GONE);
        }

//        holder.btn_update.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//                View view2 = inflater.inflate(R.layout.dialog_update_phongban, null);
//                builder.setView(view2);
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                EditText edt_pb_up = view2.findViewById(R.id.edt_updatephong);
//                EditText edt_mt_up = view2.findViewById(R.id.edt_updatemota);
//                Button btn_up = view2.findViewById(R.id.btn_update);
//
//                PhongBanDTO pb = listPhongBan.get(position);
//                edt_pb_up.setText(pb.getTenPhongBan());
//                edt_mt_up.setText(pb.getMoTaPhongBan());
//
//                btn_up.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        phongBanDAO = new PhongBanDAO(context);
//                        pb.setTenPhongBan(edt_pb_up.getText().toString().trim());
//                        pb.setMoTaPhongBan(edt_mt_up.getText().toString().trim());
//                        int check = phongBanDAO.UpdatePhongBan(pb);
//                        if (check < 0) {
//                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
//                        } else {
//                            listPhongBan.clear();
//                            listPhongBan = (ArrayList<PhongBanDTO>) phongBanDAO.getAllPhongBan();
//                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                            notifyDataSetChanged();
//                            dialog.dismiss();
//
//                        }
//                    }
//                });
//            }
//        });

        holder.btn_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view2 = inflater.inflate(R.layout.dialog_update_phongban, null);
                builder.setView(view2);
                AlertDialog dialog = builder.create();
                dialog.show();

                // Khai báo các EditText và Button trong Dialog
                EditText edt_pb_up = view2.findViewById(R.id.edt_updatephong);
                EditText edt_mt_up = view2.findViewById(R.id.edt_updatemota);
                Button btn_up = view2.findViewById(R.id.btn_update);

                // Lấy thông tin phòng ban hiện tại
                PhongBanDTO pb = listPhongBan.get(position);
                edt_pb_up.setText(pb.getTenPhongBan());
                edt_mt_up.setText(pb.getMoTaPhongBan());

                // Lưu lại mã phòng ban cũ
                int oldPhongBanId = pb.getMaPhongBan();

                btn_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        phongBanDAO = new PhongBanDAO(context);

                        // Cập nhật thông tin phòng ban mới
                        pb.setTenPhongBan(edt_pb_up.getText().toString().trim());
                        pb.setMoTaPhongBan(edt_mt_up.getText().toString().trim());

                        int check = phongBanDAO.UpdatePhongBan(pb);

                        if (check < 0) {
                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        } else {
                            // Nếu cập nhật thành công, xử lý cập nhật số lượng nhân viên trong các phòng ban
                            if (pb.getMaPhongBan() != oldPhongBanId) {
                                // Giảm số lượng nhân viên của phòng ban cũ
                                phongBanDAO.DecreaseSoLuongNhanVien(oldPhongBanId);

                                // Tăng số lượng nhân viên của phòng ban mới
                                phongBanDAO.IncreaseSoLuongNhanVien(pb.getMaPhongBan());
                            }

                            listPhongBan.clear();
                            listPhongBan = (ArrayList<PhongBanDTO>) phongBanDAO.getAllPhongBan();
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });


        holder.btn_delete.setOnClickListener(new View.OnClickListener() {

            PhongBanDTO pb = listPhongBan.get(holder.getAdapterPosition());

            @Override
            public void onClick(View v) {
                phongBanDAO = new PhongBanDAO(context);
                int check = phongBanDAO.Delete(pb.getMaPhongBan());
                if (check < 0) {
                    Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                } else  {
                    listPhongBan.clear();
                    listPhongBan = (ArrayList<PhongBanDTO>) phongBanDAO.getAllPhongBan();
                    notifyDataSetChanged();
                }
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_layout, new QuanLyThanhVienFrag()); // Adjust R.id.fragment_container to your actual container ID
                transaction.addToBackStack(null); // Optional: Adds the transaction to the back stack
                transaction.commit();
                SharedPreferences sharedPreferences1 = context.getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putInt("maphongban", phongBanDTO.getMaPhongBan());
                editor.apply();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPhongBan.size();
    }

    public static class PhongBanHolder extends RecyclerView.ViewHolder {
        TextView txt_maphongban, txt_tenphongban,txt_mota, txt_soluongnhanvien;
        ImageButton btn_update, btn_delete;

        public PhongBanHolder(@NonNull View itemView) {

            super(itemView);
            txt_maphongban = itemView.findViewById(R.id.txt_maphongban);
            txt_tenphongban = itemView.findViewById(R.id.txt_tenphongban);
            txt_mota = itemView.findViewById(R.id.txt_motaphongban);
            txt_soluongnhanvien = itemView.findViewById(R.id.txt_soluongnhanvien);
            btn_update = itemView.findViewById(R.id.btn_update);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
