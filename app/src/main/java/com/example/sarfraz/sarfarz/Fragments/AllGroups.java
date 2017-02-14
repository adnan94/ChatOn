package com.example.sarfraz.sarfarz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sarfraz.sarfarz.Group;
import com.example.sarfraz.sarfarz.MyGroupListAdaptor;
import com.example.sarfraz.sarfarz.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllGroups extends Fragment {

    ListView listView;
    ArrayList<Group> list;
    MyGroupListAdaptor adaptor;
    DatabaseReference firebase;

    public AllGroups() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_all_groups, container, false);
        firebase= FirebaseDatabase.getInstance().getReference();
        listView=(ListView)v.findViewById(R.id.allGroups);
        list=new ArrayList<>();
        adaptor=new MyGroupListAdaptor(list,getActivity());
        listView.setAdapter(adaptor);


        firebase.child("AppData").child("Groups").child("GroupList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    Group group=d.getValue(Group.class);
                    list.add(group);
                    adaptor.notifyDataSetChanged();

                }
//         String name=dataSnapshot.child("name").getValue().toString();
//                String admin=dataSnapshot.child("admin").getValue().toString();
//                String picurl=dataSnapshot.child("picurl").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return v;
    }

}
