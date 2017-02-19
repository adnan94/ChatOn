package com.example.sarfraz.sarfarz.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setPositiveButton("Add As Friend", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                fire.child("AppData").child("Friends").child(Utils.uid).child(list.get(position).getId()).setValue(new signature_friend_req(list.get(position).getName(),list.get(position).getId(),list.get(position).getPicurl()));
                fire.child("AppData").child("Friends").child(list.get(position).getId()).child(Utils.uid).setValue(new signature_friend_req(Utils.name,list.get(position).getId(),Utils.picurl));
                ////////////////////////////


            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
            }
        });
        alert.setTitle("Freind Request");
        alert.setMessage("Are you sure you want to Add ?");
        AlertDialog ad = alert.create();

        alert.show();


    }
});
        return v;
    }

}
