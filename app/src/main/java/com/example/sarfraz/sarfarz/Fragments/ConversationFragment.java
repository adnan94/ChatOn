package com.example.sarfraz.sarfarz.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sarfraz.sarfarz.Activities.ChatFragment;
import com.example.sarfraz.sarfarz.Adaptors.FriendRequestAdaptor;
import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.Utils;
import com.example.sarfraz.sarfarz.signature_friend_req;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationFragment extends Fragment {
    ListView listView;
    DatabaseReference fire;
    ArrayList<signature_friend_req> list;
    FriendRequestAdaptor adaptor;
ArrayList<String> listIds;
TextView textView;
    public ConversationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_conversation, container, false);
listIds=new ArrayList<>();
        textView=(TextView)v.findViewById(R.id.placeHolderAllConversation);
        fire = FirebaseDatabase.getInstance().getReference();
        //////////////////////////////////////////////////
        list = new ArrayList<>();
//        View v = inflater.inflate(R.layout.fragment_friend__request_, container, false);
        listView = (ListView) v.findViewById(R.id.listViewConversationScreen);
        adaptor = new FriendRequestAdaptor(list, getActivity());
        listView.setAdapter(adaptor);
        fire.child("AppData").child("Friends").child(Utils.uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               textView.setVisibility(View.GONE);
                signature_friend_req signature_friend_req = dataSnapshot.getValue(com.example.sarfraz.sarfarz.signature_friend_req.class);
                list.add(signature_friend_req);
                listIds.add(dataSnapshot.getKey());
                adaptor.notifyDataSetChanged();

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //////////////////////////////////////////
                Utils.pid=listIds.get(position);
                Utils.tempName=list.get(position).getName();
                FragmentTransaction mtransaction = getActivity().getSupportFragmentManager().beginTransaction();
                ChatFragment chatFragment = new ChatFragment();
                mtransaction.replace(R.id.container, chatFragment);
                mtransaction.addToBackStack(null);
                mtransaction.commit();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setPositiveButton("UnFriend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        fire.child("AppData").child("Friends").child(Utils.uid).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                            @Override
                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                int i = 0;
                                for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    dataSnapshot1.child(Utils.uid).child(list.get(position).getId()).getRef().removeValue();
                                    dataSnapshot1.child(list.get(position).getId()).child(Utils.uid).getRef().removeValue();

                                    list.remove(position);
                                    ContactsFragment.list.remove(position);
                                    ContactsFragment.adaptor.notifyDataSetChanged();
                                    adaptor.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


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

                return false;
            }
        });

        return v;
    }


}