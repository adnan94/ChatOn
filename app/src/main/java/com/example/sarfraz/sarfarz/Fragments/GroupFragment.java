package com.example.sarfraz.sarfarz.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {

Button createGroup;
    EditText name;
String url="";
    DatabaseReference fire;
    StorageReference storegeRef,imgRef;
   Button upload;
    ProgressDialog pd;

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
        upload=(Button)v.findViewById(R.id.buttonUploadGroupScreen);
        pd=new ProgressDialog(getActivity());
        pd.setTitle("Uploading ..");
        pd.setMessage("Wait while uploading..");
        storegeRef = FirebaseStorage.getInstance().getReference();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url="";
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
            }
        });


        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               if(name.getText().toString().equals("")){
                   Toast.makeText(getActivity(),"Enter Group Name",Toast.LENGTH_SHORT).show();
               }else{
                   Map<String,String> map;
                 if(url.equals("")){
                     map=new HashMap<String, String>();
                     map.put("name",name.getText().toString());
                     map.put("picurl", "N/A");
                     map.put("admin",Utils.name);
                 }else{
                     map=new HashMap<String, String>();
                     map.put("name",name.getText().toString());
                     map.put("picurl", url);
                     map.put("admin",Utils.name);
                 }





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
        File f=new File(path);

        imgRef = storegeRef.child("imageOnly").child(f.getName());

        UploadTask uploadTask = imgRef.putFile(file);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                url=taskSnapshot.getDownloadUrl().toString();
                pd.dismiss();



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();

                Toast.makeText(getActivity(), "Uploading Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", e.getMessage().toString());
            }
        });

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && data != null ){
            pd.show();
            String path = getRealPathFromURI(data.getData());
            getImage(path,data);
        }
    }
}
