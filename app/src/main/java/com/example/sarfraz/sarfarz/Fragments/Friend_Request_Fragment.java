package com.example.sarfraz.sarfarz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sarfraz.sarfarz.Adaptors.FriendRequestAdaptor;
import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.Utils;
import com.example.sarfraz.sarfarz.signature_friend_req;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Friend_Request_Fragment extends Fragment {
    ListView listView;
    DatabaseReference fire;
    ArrayList<signature_friend_req> list;
    FriendRequestAdaptor adaptor;


    public Friend_Request_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fire = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
        View v = inflater.inflate(R.layout.fragment_friend__request_, container, false);
        listView = (ListView) v.findViewById(R.id.listViewRequestScreen);
        adaptor = new FriendRequestAdaptor(list, getActivity());
        listView.setAdapter(adaptor);
        fire.child("AppData").child("FriendRequest").child(Utils.uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             for(DataSnapshot d:dataSnapshot.getChildren()){
                 signature_friend_req signature_friend_req = d.getValue(com.example.sarfraz.sarfarz.signature_friend_req.class);
                 list.add(signature_friend_req);
                 adaptor.notifyDataSetChanged();
             }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return v;
    }

}