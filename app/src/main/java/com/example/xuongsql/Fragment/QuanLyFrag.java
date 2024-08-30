package com.example.xuongsql.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuongsql.Adapter.PhongBanAdapter;
import com.example.xuongsql.DAO.PhongBanDAO;
import com.example.xuongsql.DTO.PhongBanDTO;
import com.example.xuongsql.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuanLyFrag extends Fragment {
    FloatingActionButton btn_addPhongBan;
    RecyclerView rc_phongban;
    PhongBanDAO phongBanDAO;
    PhongBanAdapter adapterPhongBan;
    ArrayList<PhongBanDTO> listPhongBan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quanlyphongban, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rc_phongban = view.findViewById(R.id.rcv_phongban);
        btn_addPhongBan = view.findViewById(R.id.btn_add_phongban);
        phongBanDAO = new PhongBanDAO(getContext());
        listPhongBan = (ArrayList<PhongBanDTO>) phongBanDAO.getAllPhongBan();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("chucvu", "");
        if (!userRole.equals("Admin")) {
            btn_addPhongBan.setVisibility(View.GONE);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rc_phongban.setLayoutManager(layoutManager);
        adapterPhongBan = new PhongBanAdapter(getActivity(), listPhongBan);
        rc_phongban.setAdapter(adapterPhongBan);


        btn_addPhongBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view1 = inflater.inflate(R.layout.dialog_phongban, null);
                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();
                
                EditText edt_tenpb = view1.findViewById(R.id.edt_phongban);
                EditText edt_mota = view1.findViewById(R.id.edt_motaphongban);
                Button btn_luu = view1.findViewById(R.id.btn_luupb);

                btn_luu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhongBanDTO phongBanDTO = new PhongBanDTO();
                        String tenph = edt_tenpb.getText().toString();
                        String mota = edt_mota.getText().toString();
                        phongBanDTO.setTenPhongBan(tenph);
                        phongBanDTO.setMoTaPhongBan(mota);

                       //int kq = phongBanDAO.addPhongBan(phongBanDTO);
                        listPhongBan = (ArrayList<PhongBanDTO>) phongBanDAO.getAllPhongBan();
                        adapterPhongBan = new PhongBanAdapter(getContext(), listPhongBan);
                        rc_phongban.setAdapter(adapterPhongBan);
                        dialog.dismiss();

                    }
                });

            }
        });


    }
}
