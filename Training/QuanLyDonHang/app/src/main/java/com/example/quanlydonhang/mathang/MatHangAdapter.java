package com.example.quanlydonhang.mathang;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlydonhang.R;

import java.util.ArrayList;

public class MatHangAdapter extends BaseAdapter {
    private Context context;
    ArrayList<MatHang> data;

    public MatHangAdapter(Context context, ArrayList<MatHang> data) {
        this.context = context;
        this.data = data;
    }

    static class MatHangHolder {
        TextView maHG, tenHG, dacDiem, dVT, donGia;
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
        MatHangHolder holder = null;
        if(row != null)         {
            holder = (MatHangHolder) row.getTag();
        }
        else
        {
            holder = new MatHangHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_mat_hang, viewGroup, false);

            holder.maHG = row.findViewById(R.id.textViewMaMHAdapter);
            holder.tenHG = row.findViewById(R.id.textViewTenMHAdapter);
            holder.dacDiem = row.findViewById(R.id.textViewDacDiemAdapter);
            holder.dVT = row.findViewById(R.id.textViewDVTAdapter);
            holder.donGia = row.findViewById(R.id.textViewDonGiaAdapter);

            row.setTag(holder);
        }
        MatHang matHang = data.get(i);

        holder.maHG.setText(matHang.getMAHG());
        holder.tenHG.setText(matHang.getTENHG());
        holder.dacDiem.setText(matHang.getDACDIEM());
        holder.dVT.setText(matHang.getDVT());
        holder.donGia.setText(matHang.getDONGIA()+"");

        return row;
    }
}
