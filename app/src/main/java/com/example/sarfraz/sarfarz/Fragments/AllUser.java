package com.example.sarfraz.sarfarz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sarfraz.sarfarz.Adaptors.ConversationAdaptor;
import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.user;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllUser extends Fragment {

    ListView listView;
    DatabaseReference databaseReference;
    ArrayList<user> list;
    ConversationAdaptor adaptor;

    public AllUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_all_user, container, false);
        list=new ArrayList<user>();
        adaptor=new ConversationAdaptor(list,getActivity());

        listView = (ListView) v.findViewById(R.id.listViewConversationScreen);
        listView.setAdapter(adaptor);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        getData();
        return v;
    }
    public void getData() {
        databaseReference.child("AppData").child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user u = dataSnapshot.getValue(user.class);
                list.add(u);
                adaptor.notifyDataSetChanged();
//
//

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
