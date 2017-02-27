package com.example.sarfraz.sarfarz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sarfraz on 2/14/2017.
 */

public class MyGroupListAdaptor extends BaseAdapter {
    ArrayList<Group> list;
    LayoutInflater inflater;
    Context context;

    public MyGroupListAdaptor(ArrayList<Group> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
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
        View v = inflater.inflate(R.layout.single_item, null, false);
        TextView name = (TextView) v.findViewById(R.id.textViewNameSingleItem);
        TextView admin = (TextView) v.findViewById(R.id.textViewSingleItem);

        de.hdodenhof.circleimageview.CircleImageView iv = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.imageViewSingleItem);
        Picasso.with(context).load(list.get(position).getPicurl()).placeholder(R.drawable.user).into(iv);

        name.setText(list.get(position).getName());
        admin.setText(list.get(position).getAdmin());
        return v;
    }
}
