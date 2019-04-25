package com.example.quanlydonhang.khachhang;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlydonhang.R;

import java.util.ArrayList;

public class KhachHangAdapter extends BaseAdapter {
    private Context context;
    ArrayList<KhachHang> data;

    public KhachHangAdapter(Context context, ArrayList<KhachHang> data) {
        this.context = context;
        this.data = data;
    }

    static class KhachHangHolder {
        TextView maKH, tenKH;
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
        KhachHangHolder holder = null;
        if(row != null)         {
            holder = (KhachHangHolder) row.getTag();
        }
        else
        {
            holder = new KhachHangHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_khach_hang, viewGroup, false);

            holder.maKH = row.findViewById(R.id.textViewKHMaKH);
            holder.tenKH = row.findViewById(R.id.textViewKHTenKH);

            row.setTag(holder);
        }
        KhachHang khachHang = data.get(i);

        holder.maKH.setText(khachHang.getMaKH());
        holder.tenKH.setText(khachHang.getTenKH());

        return row;
    }
}
