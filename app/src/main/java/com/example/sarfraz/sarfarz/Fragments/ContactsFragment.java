package com.example.sarfraz.sarfarz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
public class ContactsFragment extends Fragment {
    ListView listView;
    DatabaseReference fire;
    public static ArrayList<signature_friend_req> list;
    public static FriendRequestAdaptor adaptor;
    TextView textView;


    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);
        textView=(TextView)v.findViewById(R.id.placeHolderAllContacts);

        fire = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
//        View v = inflater.inflate(R.layout.fragment_friend__request_, container, false);
        listView = (ListView) v.findViewById(R.id.listViewContactScreen);
        adaptor = new FriendRequestAdaptor(list, getActivity());
        listView.setAdapter(adaptor);
        fire.child("AppData").child("Friends").child(Utils.uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                textView.setVisibility(View.GONE);
                signature_friend_req signature_friend_req = dataSnapshot.getValue(com.example.sarfraz.sarfarz.signature_friend_req.class);
                list.add(signature_friend_req);
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

               Utils.tempFriendUid=list.get(position).getId();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FriendProfile profile = new FriendProfile();
                transaction.replace(R.id.container, profile);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return v;
    }

}
