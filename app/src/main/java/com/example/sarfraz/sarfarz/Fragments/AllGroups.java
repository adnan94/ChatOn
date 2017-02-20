package com.example.sarfraz.sarfarz.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sarfraz.sarfarz.Group;
import com.example.sarfraz.sarfarz.MyGroupListAdaptor;
import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.Utils;
import com.example.sarfraz.sarfarz.signature_friend_req;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllGroups extends Fragment {

    ListView listView;
    ArrayList<Group> list;
    MyGroupListAdaptor adaptor;
    DatabaseReference firebase;
TextView textView;
    public AllGroups() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_all_groups, container, false);
        textView=(TextView)v.findViewById(R.id.placeHolderAllGroups);
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
                    textView.setVisibility(View.GONE);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setPositiveButton("Join Group", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("name", list.get(position).getName());
                        map.put("picurl", "N/A");
                        map.put("admin", list.get(position).getAdmin());

                        firebase.child("AppData").child("Groups").child("MyGroup").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(list.get(position).getName()).setValue(map);

                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
                    }
                });
                alert.setTitle("Group");
                alert.setMessage("Are you sure you want to Join ?");
                AlertDialog ad = alert.create();

                alert.show();




            }});

        return v;
    }

}
