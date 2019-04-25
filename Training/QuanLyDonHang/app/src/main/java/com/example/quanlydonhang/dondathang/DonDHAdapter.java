package com.example.quanlydonhang.dondathang;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlydonhang.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DonDHAdapter extends BaseAdapter {

    private Context context;
    ArrayList<DonDH> data;

    public DonDHAdapter(Context context, ArrayList<DonDH> data) {
        this.context = context;
        this.data = data;
    }

    static class DonDHHolder {
        TextView soDDH, maKH, ngayDH, soNgay, tinhTrang;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.indexOf(getItem(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        DonDHHolder holder = null;
        if(row != null)         {
            holder = (DonDHHolder) row.getTag();
        }
        else
        {
            holder = new DonDHHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_don_dat_hang, viewGroup, false);

            holder.soDDH = row.findViewById(R.id.textViewSoDDH);
            holder.maKH = row.findViewById(R.id.textViewMaKH);
            holder.ngayDH = row.findViewById(R.id.textViewNgayDH);
            holder.soNgay = row.findViewById(R.id.textViewSoNgay);
            holder.tinhTrang = row.findViewById(R.id.textViewTinhTrang);

            row.setTag(holder);
        }
        DonDH donDH = data.get(i);

        holder.soDDH.setText(String.valueOf(donDH.getSoDH()));
        holder.maKH.setText(donDH.getMaKH());
        String da = donDH.getStringNgayDH();
        holder.ngayDH.setText(da);
        holder.soNgay.setText(String.valueOf(donDH.getSoNgay()));
        holder.tinhTrang.setText(donDH.getTinhTrang());

        return row;
    }
}
