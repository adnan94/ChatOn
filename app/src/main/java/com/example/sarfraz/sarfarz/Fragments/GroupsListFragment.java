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

import com.example.sarfraz.sarfarz.Group;
import com.example.sarfraz.sarfarz.MyGroupListAdaptor;
import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsListFragment extends Fragment {
    ListView listView;
    ArrayList<Group> list;
    MyGroupListAdaptor adaptor;
    DatabaseReference firebase;
   TextView textView;


    public GroupsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_groups_list, container, false);
        textView=(TextView)v.findViewById(R.id.placeHolderMyGroups);
        firebase= FirebaseDatabase.getInstance().getReference();
        listView=(ListView)v.findViewById(R.id.myGroupList);
        list=new ArrayList<>();
        adaptor=new MyGroupListAdaptor(list,getActivity());
        listView.setAdapter(adaptor);



        firebase.child("AppData").child("Groups").child("MyGroup").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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
            public void onItemClick(AdapterView<?> parent,View view, int position, long id) {
                Utils.groupName=list.get(position).getName();
                FragmentTransaction mtransaction = getActivity().getSupportFragmentManager().beginTransaction();
                GroupChat groupChat = new GroupChat();
                mtransaction.replace(R.id.container, groupChat);
                mtransaction.addToBackStack(null);
                mtransaction.commit();

            }
        });
        return v;
    }


}
