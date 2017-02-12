package com.example.sarfraz.sarfarz.Fragments;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {

Button createGroup;
    EditText name;
    DatabaseReference fire;

    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_blank, container, false);
        fire= FirebaseDatabase.getInstance().getReference();
        createGroup=(Button)v.findViewById(R.id.buttonCreateGroupScreen);
        name=(EditText)v.findViewById(R.id.editTextGroupScreen);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               if(name.getText().toString().equals("")){
                   Toast.makeText(getActivity(),"Enter Group Name",Toast.LENGTH_SHORT).show();
               }else{
                   Map<String,String> map=new HashMap<String, String>();
                   map.put("name",name.getText().toString());
                   map.put("picurl", Utils.picurl);
                   map.put("admin",Utils.name);
                   fire.child("AppData").child("Groups").child("GroupList").child(name.getText().toString()).setValue(map);
                   fire.child("AppData").child("Groups").child("MyGroup").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(name.getText().toString()).setValue(map, new DatabaseReference.CompletionListener() {
                       @Override
                       public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                           Toast.makeText(getActivity(),"Updated",Toast.LENGTH_SHORT).show();
                           name.setText("");
                       }
                   });

               }

            }
        });




        return v;
    }

}
