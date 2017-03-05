package com.example.sarfraz.sarfarz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sarfraz.sarfarz.Adaptors.pagerAdaptor;
import com.example.sarfraz.sarfarz.Fragments.AllGroups;
import com.example.sarfraz.sarfarz.Fragments.AllUser;
import com.example.sarfraz.sarfarz.Fragments.Friend_Request_Fragment;
import com.example.sarfraz.sarfarz.Fragments.GroupFragment;
import com.example.sarfraz.sarfarz.Fragments.MyProfile;
import com.example.sarfraz.sarfarz.Fragments.StatusFragment;
import com.example.sarfraz.sarfarz.Fragments.UpdateInfo;
import com.example.sarfraz.sarfarz.Notificationn;
import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.Utils;
import com.example.sarfraz.sarfarz.user;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager manager;
    TabLayout tabLayout;
    ViewPager viewPager;
    DatabaseReference fire;
    StorageReference storegeRef, imgRef;
    de.hdodenhof.circleimageview.CircleImageView iv;
    ProgressDialog pd;
    TextView textView;
    public static NavDrawerActivity context;
    String array[] = {"Conversation", "Contacts", "Groups"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Intent i = new Intent(this, Notificationn.class);
        startService(i);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pd = new ProgressDialog(this);
        pd.setTitle("Uploading");
        pd.setMessage("Wait While Uploading !");
        context = this;
        fire = FirebaseDatabase.getInstance().getReference();
        storegeRef = FirebaseStorage.getInstance().getReference();
        getUserData();
//        setNav();
        manager = getSupportFragmentManager();
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);
        pagerAdaptor pagerAdapter = new pagerAdaptor(manager, array);
        viewPager.setAdapter(pagerAdapter);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        textView = (TextView) findViewById(R.id.textViewNav);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Utils.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack(getSupportFragmentManager().getBackStackEntryAt(1).getId(), getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void getUserData() {
        fire.child("AppData").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user u = dataSnapshot.getValue(user.class);
//
//

                Utils.name = u.getName();
                Utils.birthday = u.getBirthday();
                Utils.contact = u.getContact();
                Utils.email = u.getEmail();
                Utils.status = u.getStatus();
                Utils.picurl = u.getPicurl();
                setNav(u.getName(), u.getEmail(), u.getPicurl());
                textView.setText(Utils.name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void setNav(String name, String email, String url) {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView nameNAv = (TextView) header.findViewById(R.id.nameNav);
        TextView emailNav = (TextView) header.findViewById(R.id.emailNav);
        iv = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imageViewNav);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(NavDrawerActivity.this,"hello !",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
            }
        });
        Picasso.with(NavDrawerActivity.this).load(url).placeholder(R.drawable.user).into(iv);
        nameNAv.setText(name);
        emailNav.setText(email);

    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
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
                Toast.makeText(NavDrawerActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", Utils.name);
                map.put("picurl", taskSnapshot.getDownloadUrl().toString());
                map.put("email", Utils.email);
                map.put("status", Utils.status);
                map.put("contact", Utils.contact);
                map.put("birthday", Utils.birthday);

                fire.child("AppData").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(map, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                        pd.dismiss();
                        Toast.makeText(NavDrawerActivity.this, "Sucessfull",
                                Toast.LENGTH_SHORT).show();

                    }
                });
                try {
                    Bitmap ii = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    iv.setImageBitmap(ii);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();

                Toast.makeText(NavDrawerActivity.this, "Uploading Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", e.getMessage().toString());
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            FragmentTransaction transaction = manager.beginTransaction();
            MyProfile profile = new MyProfile();
            transaction.replace(R.id.container, profile);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_group) {
            FragmentTransaction transaction = manager.beginTransaction();
            GroupFragment group = new GroupFragment();
            transaction.replace(R.id.container, group);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_update_info) {
            FragmentTransaction mtransaction = manager.beginTransaction();
            UpdateInfo updateInfo = new UpdateInfo();
            mtransaction.replace(R.id.container, updateInfo);
            mtransaction.addToBackStack(null);
            mtransaction.commit();
        } else if (id == R.id.nav_status) {
            FragmentTransaction mtransaction = manager.beginTransaction();
            StatusFragment statusFragment = new StatusFragment();
            mtransaction.replace(R.id.container, statusFragment);
            mtransaction.addToBackStack(null);
            mtransaction.commit();
        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(NavDrawerActivity.this, SignInActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_allGroups) {
            FragmentTransaction mtransaction = manager.beginTransaction();
            AllGroups allGroups = new AllGroups();
            mtransaction.replace(R.id.container, allGroups);
            mtransaction.addToBackStack(null);
            mtransaction.commit();

        } else if (id == R.id.nav_requests) {

            FragmentTransaction mtransaction = manager.beginTransaction();
            Friend_Request_Fragment requestFragment = new Friend_Request_Fragment();
            mtransaction.replace(R.id.container, requestFragment);
            mtransaction.addToBackStack(null);
            mtransaction.commit();
        } else if (id == R.id.nav_users) {
            FragmentTransaction mtransaction = manager.beginTransaction();
            AllUser userAll = new AllUser();
            mtransaction.replace(R.id.container, userAll);
            mtransaction.addToBackStack(null);
            mtransaction.commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
