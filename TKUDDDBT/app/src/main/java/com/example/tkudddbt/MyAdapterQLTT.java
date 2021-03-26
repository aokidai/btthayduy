package com.example.tkudddbt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyAdapterQLTT extends ArrayAdapter<pesion> {
    ArrayList<pesion> dsQLTT = new ArrayList<pesion>();
    public MyAdapterQLTT(Context context, int resource, ArrayList<pesion> object){
        super(context, resource, object);
        dsQLTT = object;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.mymainactivity, null);
        TextView txtTen = (TextView) v.findViewById(R.id.tvTen);
        TextView txtSoDT = (TextView) v.findViewById(R.id.tvSoDT);
        TextView txtDiaChi = (TextView) v.findViewById(R.id.tvDiaChi);
        txtTen.setText(dsQLTT.get(position).getTen());
        txtSoDT.setText(dsQLTT.get(position).getSoDT());
        txtDiaChi.setText(dsQLTT.get(position).getDiaChi());
        return v;
    }
}
