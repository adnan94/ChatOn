package com.example.sarfraz.sarfarz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.Utils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends Fragment {
TextView name,email,status,birthday,contact;
ImageView imageViewMyProfile;
    public MyProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_profile, container, false);
        name=(TextView)view.findViewById(R.id.textViewMyProfile);
        email=(TextView)view.findViewById(R.id.textViewEmailwMyProfile);
        status=(TextView)view.findViewById(R.id.textViewStatusMyProfile);
        birthday=(TextView)view.findViewById(R.id.textViewBirthdayMyProfile);
        contact=(TextView)view.findViewById(R.id.textViewContact);
        contact=(TextView)view.findViewById(R.id.textViewContact);
        imageViewMyProfile=(ImageView)view.findViewById(R.id.imageViewMyProfile);

        name.setText(Utils.name);
        email.setText(Utils.email);
        status.setText(Utils.status);
        birthday.setText(Utils.birthday);
        contact.setText(Utils.contact);

        Picasso.with(getActivity()).load(Utils.picurl).placeholder(R.drawable.user).into(imageViewMyProfile);


        return view;
    }

}
