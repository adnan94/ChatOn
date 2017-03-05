package com.example.sarfraz.sarfarz.Activities;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sarfraz.sarfarz.Adaptors.chatAdaptor;
import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.Utils;
import com.example.sarfraz.sarfarz.chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    EditText message;
    ListView listView;
    ImageButton imageButton, imageUpload;
    DatabaseReference fire;
    ArrayList<chat> list;
    chatAdaptor adaptor;
    TextView textViewNav;
    StorageReference storegeRef,imgRef;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_chat, container, false);
        fire = FirebaseDatabase.getInstance().getReference();
//        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
//        fab.setVisibility(View.GONE);
        textViewNav = (TextView) getActivity().findViewById(R.id.textViewNav);
        textViewNav.setText(Utils.tempName);
        list = new ArrayList<>();
        listView = (ListView) v.findViewById(R.id.listViewGroupChat);
        message = (EditText) v.findViewById(R.id.editTextGroupSent);
        imageButton = (ImageButton) v.findViewById(R.id.imageButtonGroupSent);
        imageUpload = (ImageButton) v.findViewById(R.id.imageUploadChat);
        storegeRef = FirebaseStorage.getInstance().getReference();

        adaptor = new chatAdaptor(list, getActivity());

        listView.setAdapter(adaptor);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setStackFromBottom(true);
        listView.setAdapter(adaptor);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fire.child("AppData").child("Conversations").child("OneToOne").child(Utils.uid).child(Utils.pid).push().setValue(new chat(Utils.name, Utils.picurl, Utils.uid, "message", message.getText().toString()));
                fire.child("AppData").child("Conversations").child("OneToOne").child(Utils.pid).child(Utils.uid).push().setValue(new chat(Utils.name, Utils.picurl, Utils.uid, "message", message.getText().toString()), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        fire.child("AppData").child("Notificationn").child("OneToOne").child(Utils.pid).push().setValue(new chat(Utils.name, Utils.picurl, Utils.uid, "message", message.getText().toString()), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//
                                message.setText("");

                            }
                        });

                    }

                });
                imageUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, 0);


                    }

                });


            }
        });

//
        getConversation();
//


        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && data != null) {
//            pd.show();
            String path = getRealPathFromURI(data.getData());
            getImage(path, data);
        }

    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public void getImage(String path, final Intent data) {
        Uri file = Uri.fromFile(new File(path));
        Log.d("TAG", file.toString());

//        StorageReference riversRef = storegeRef.child("image/");
        File f = new File(path);

        imgRef = storegeRef.child("imageOnly").child(f.getName());

        UploadTask uploadTask = imgRef.putFile(file);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                fire.child("AppData").child("Conversations").child("OneToOne").child(Utils.uid).child(Utils.pid).push().setValue(new chat(Utils.name, taskSnapshot.getDownloadUrl().toString(), Utils.uid, "image", message.getText().toString()));
                fire.child("AppData").child("Conversations").child("OneToOne").child(Utils.pid).child(Utils.uid).push().setValue(new chat(Utils.name, taskSnapshot.getDownloadUrl().toString(), Utils.uid, "image", message.getText().toString()), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        fire.child("AppData").child("Notificationn").child("OneToOne").child(Utils.pid).push().setValue(new chat(Utils.name, Utils.picurl, Utils.uid, "image", Utils.name+" sent You a image"), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//
                                        message.setText("");
                                    }

                                });
 }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                pd.dismiss();

                Toast.makeText(getActivity(), "Uploading Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", e.getMessage().toString());
            }
        });

    }


    public void getConversation() {
        fire.child("AppData").child("Conversations").child("OneToOne").child(Utils.uid).child(Utils.pid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chat c = dataSnapshot.getValue(chat.class);
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

    @Override
    public void onDestroy() {
        textViewNav.setText(Utils.name);
        super.onDestroy();
    }
}
