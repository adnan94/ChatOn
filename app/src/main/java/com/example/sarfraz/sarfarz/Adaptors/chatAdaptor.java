package com.example.sarfraz.sarfarz.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.Utils;
import com.example.sarfraz.sarfarz.chat;

import java.util.ArrayList;

/**
 * Created by Microsoft on 2/16/2017.
 */

public class chatAdaptor extends BaseAdapter {
    ArrayList<chat> list;
    LayoutInflater inflater;
    Context context;

    public chatAdaptor(ArrayList<chat> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
View v;
        if(list.get(position).getId().equals(Utils.uid)){

            v=inflater.inflate(R.layout.right,null,false);
            TextView name=(TextView) v.findViewById(R.id.textViewName);
            TextView message=(TextView) v.findViewById(R.id.textViewMessage);
            name.setText(list.get(position).getName());
            message.setText(list.get(position).getMessage());

        }else{
            v=inflater.inflate(R.layout.left,null,false);
            TextView name=(TextView) v.findViewById(R.id.textViewName);
            TextView message=(TextView) v.findViewById(R.id.textViewMessage);
            name.setText(list.get(position).getName());
            message.setText(list.get(position).getMessage());
        }
        return v;
    }
}
