package com.example.sarfraz.sarfarz.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sarfraz.sarfarz.Fragments.GroupFragment;
import com.example.sarfraz.sarfarz.Fragments.MyProfile;
import com.example.sarfraz.sarfarz.Fragments.StatusFragment;
import com.example.sarfraz.sarfarz.Fragments.UpdateInfo;
import com.example.sarfraz.sarfarz.R;
import com.example.sarfraz.sarfarz.pagerAdaptor;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager manager;
    TabLayout tabLayout;
    ViewPager viewPager;
String array []={"Conversation","Contacts","Groups"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager=getSupportFragmentManager();
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);
        pagerAdaptor pagerAdapter=new pagerAdaptor(manager,array);
        viewPager.setAdapter(pagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount()>1){
        getSupportFragmentManager().popBackStack(getSupportFragmentManager().getBackStackEntryAt(1).getId(),getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);

       }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            FragmentTransaction transaction=manager.beginTransaction();
            MyProfile profile=new MyProfile();
            transaction.replace(R.id.container,profile);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_group) {
            FragmentTransaction transaction=manager.beginTransaction();
            GroupFragment group=new GroupFragment();
            transaction.replace(R.id.container,group);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_update_info) {
            FragmentTransaction mtransaction=manager.beginTransaction();
            UpdateInfo updateInfo=new UpdateInfo();
            mtransaction.replace(R.id.container,updateInfo);
            mtransaction.addToBackStack(null);
            mtransaction.commit();
        } else if (id == R.id.nav_status) {
            FragmentTransaction mtransaction=manager.beginTransaction();
            StatusFragment statusFragment=new StatusFragment();
            mtransaction.replace(R.id.container,statusFragment);
            mtransaction.addToBackStack(null);
            mtransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
