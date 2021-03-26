package com.example.tkudddbt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyQueQuan extends ArrayAdapter<quequan> {
    ArrayList<quequan> dsQueQuan = new ArrayList<quequan>();
    public MyQueQuan(Context context, int resource, ArrayList<quequan> object){
        super(context, resource, object);
        dsQueQuan = object;
    }

}
