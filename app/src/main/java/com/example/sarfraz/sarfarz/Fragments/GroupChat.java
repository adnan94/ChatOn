package com.example.sarfraz.sarfarz.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.sarfraz.sarfarz.Adaptors.chatAdaptor;
import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.Utils;
import com.example.sarfraz.sarfarz.chat;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupChat extends Fragment {
EditText message;
ListView listView;
    ImageButton imageButton;
    DatabaseReference fire;
ArrayList<chat> list;
chatAdaptor adaptor;



    public GroupChat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_group_chat, container, false);
        fire= FirebaseDatabase.getInstance().getReference();
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
list=new ArrayList<>();
listView=(ListView)v.findViewById(R.id.listViewGroupChat);
        message=(EditText)v.findViewById(R.id.editTextGroupSent);
        imageButton=(ImageButton)v.findViewById(R.id.imageButtonGroupSent);
adaptor=new chatAdaptor(list,getActivity());

listView.setAdapter(adaptor);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fire.child("AppData").child("Conversations").child("GroupChat").child(Utils.groupName).push().setValue(new chat(Utils.name,Utils.picurl,Utils.uid,"message",message.getText().toString()));
            message.setText("");
            }
        });


        getConversation();




        return v;
    }
public void getConversation(){
    fire.child("AppData").child("Conversations").child("GroupChat").child(Utils.groupName).addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            chat c=dataSnapshot.getValue(chat.class);
list.add(c);
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
    }
}
