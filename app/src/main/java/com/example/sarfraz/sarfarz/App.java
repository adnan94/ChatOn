package com.example.sarfraz.sarfarz;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by adnan on 2/12/2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
