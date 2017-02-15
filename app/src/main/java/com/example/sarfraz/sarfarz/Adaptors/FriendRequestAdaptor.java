package com.example.sarfraz.sarfarz.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarfraz.sarfarz.Group;
import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.signature_friend_req;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by adnan on 2/15/2017.
 */

public class FriendRequestAdaptor extends BaseAdapter {
    ArrayList<signature_friend_req> list;
    LayoutInflater inflater;
    Context context;

    public FriendRequestAdaptor(ArrayList<signature_friend_req> list, Context context) {
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
        View v=inflater.inflate(R.layout.single_item,null,false);
        TextView name=(TextView)v.findViewById(R.id.textViewNameSingleItem);
        TextView admin=(TextView)v.findViewById(R.id.textViewSingleItem);

        ImageView iv=(ImageView)v.findViewById(R.id.imageViewSingleItem);
        Picasso.with(context).load(list.get(position).getPicurl()).placeholder(R.drawable.user).into(iv);

        name.setText(list.get(position).getName());
        admin.setText("");
        return v;
    }


}
