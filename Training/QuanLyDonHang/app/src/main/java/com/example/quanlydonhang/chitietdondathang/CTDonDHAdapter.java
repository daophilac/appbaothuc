package com.example.quanlydonhang.chitietdondathang;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlydonhang.R;

import java.util.ArrayList;

public class CTDonDHAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CTDonDH> data;

    public CTDonDHAdapter(Context context, ArrayList<CTDonDH> data) {
        this.context = context;
        this.data = data;
    }

    static class CTDonDHHolder {
        TextView soDDH, maMH, slDat;
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
        CTDonDHHolder holder = null;
        if(row != null)         {
            holder = (CTDonDHHolder) row.getTag();
        }
        else
        {
            holder = new CTDonDHHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_ct_don_dh, viewGroup, false);

            holder.soDDH = row.findViewById(R.id.textViewSoDDHCT);
            holder.maMH = row.findViewById(R.id.textViewMaHGCT);
            holder.slDat = row.findViewById(R.id.textViewSLDATCT);

            row.setTag(holder);
        }
        CTDonDH ctDonDH = data.get(i);

        holder.soDDH.setText(String.valueOf(ctDonDH.getSODDH()));
        holder.maMH.setText(ctDonDH.getMAHG());
        holder.slDat.setText(String.valueOf(ctDonDH.getSLDAT()));

        return row;
    }
}
