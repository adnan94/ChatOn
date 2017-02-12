package com.example.sarfraz.sarfarz.Adaptors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sarfraz.sarfarz.Fragments.ContactsFragment;
import com.example.sarfraz.sarfarz.Fragments.ConversationFragment;
import com.example.sarfraz.sarfarz.Fragments.GroupsListFragment;

/**
 * Created by Sarfraz on 2/2/2017.
 */

public class pagerAdaptor extends FragmentStatePagerAdapter {

    String arr[];


    public pagerAdaptor(FragmentManager fm,String array []) {
        super(fm);
        this.arr=array;
    }

    @Override
    public Fragment getItem(int position) {
       Fragment fragment=null;
        if(position==0){
            fragment=new ConversationFragment();
        }
        if(position==1){
            fragment=new ContactsFragment();
        }
        if(position==2){
            fragment=new GroupsListFragment();
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arr[position];
    }

    @Override
    public int getCount() {
        return arr.length;
    }
}
