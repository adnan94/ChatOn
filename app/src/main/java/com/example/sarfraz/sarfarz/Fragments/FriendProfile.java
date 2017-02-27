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
import com.example.sarfraz.sarfarz.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by adnan on 2/20/2017.
 */

public class FriendProfile extends Fragment{
    TextView name,email,status,birthday,contact;
    de.hdodenhof.circleimageview.CircleImageView imageViewMyProfile;
    public FriendProfile() {
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
imageViewMyProfile=(de.hdodenhof.circleimageview.CircleImageView)view.findViewById(R.id.imageViewMyProfile);

        FirebaseDatabase.getInstance().getReference().child("AppData").child("Users").child(Utils.tempFriendUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot d:dataSnapshot.getChildren()){
                    user u=dataSnapshot.getValue(user.class);

                    name.setText(u.getName());
                    email.setText(u.getEmail());
                    status.setText(u.getStatus());
                    birthday.setText(u.getBirthday());
                    contact.setText(u.getContact());

                    Picasso.with(getActivity()).load(u.getPicurl()).placeholder(R.drawable.user).into(imageViewMyProfile);
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }



}
