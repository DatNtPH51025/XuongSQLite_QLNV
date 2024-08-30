package com.example.xuongsql.Adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.xuongsql.DTO.PhongBanDTO;
import com.example.xuongsql.R;

import java.util.ArrayList;

//public class SpinerAdapter extends ArrayAdapter<PhongBanDTO> {
//    private Context context;
//    private ArrayList<PhongBanDTO> list;
//    TextView txt_pb_sp;
//
//    public SpinerAdapter(@NonNull Context context, @NonNull ArrayList<PhongBanDTO> list) {
//        super(context, 0, list);
//        this.context = context;
//        this.list = list;
//
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View view = convertView;
//        if (view == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = View.inflate(context, R.layout.sp_layout, null);
//        }
//        final  PhongBanDTO phongBanDTO = list.get(position);
//        if (phongBanDTO != null) {
//            txt_pb_sp = view.findViewById(R.id.tx_tenpb_sp);
//            txt_pb_sp.setText(phongBanDTO.getTenPhongBan());
//
//        }
//        return view;
//    }
//
//    @Override
//    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View view = convertView;
//        if (view == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = View.inflate(context, R.layout.sp_layout, null);
//        }
//        final  PhongBanDTO phongBanDTO = list.get(position);
//        if (phongBanDTO != null) {
//            txt_pb_sp = view.findViewById(R.id.tx_tenpb_sp);
//            txt_pb_sp.setText(phongBanDTO.getTenPhongBan());
//
//        }
//        return view;
//    }
//}

public class SpinerAdapter extends ArrayAdapter<PhongBanDTO> {
    private Context context;
    private ArrayList<PhongBanDTO> list;
    TextView txt_pb_sp;

    public SpinerAdapter(@NonNull Context context, @NonNull ArrayList<PhongBanDTO> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sp_layout, parent, false);
        }

        PhongBanDTO phongBanDTO = list.get(position);
        TextView txt_pb_sp = convertView.findViewById(R.id.tx_tenpb_sp);
        txt_pb_sp.setText(phongBanDTO.getTenPhongBan());

        // Customization for selected item view
        txt_pb_sp.setPadding(16, 16, 16, 16);
        txt_pb_sp.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        txt_pb_sp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sp_layout, parent, false);
        }

        PhongBanDTO phongBanDTO = list.get(position);
        TextView txt_pb_sp = convertView.findViewById(R.id.tx_tenpb_sp);
        txt_pb_sp.setText(phongBanDTO.getTenPhongBan());

        // Customization for dropdown item view
        txt_pb_sp.setPadding(16, 16, 16, 16);
        txt_pb_sp.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        txt_pb_sp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        return convertView;
    }
}

