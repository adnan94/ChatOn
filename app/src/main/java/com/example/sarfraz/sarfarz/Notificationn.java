package com.example.sarfraz.sarfarz;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import static android.R.attr.bitmap;

public class Notificationn extends Service {
    DatabaseReference fire;

    public Notificationn() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Started", Toast.LENGTH_SHORT).show();

        fire = FirebaseDatabase.getInstance().getReference();
        fire.child("AppData").child("Notificationn").child("OneToOne").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Toast.makeText(getApplicationContext(),    dataSnapshot.getValue().toString(),Toast.LENGTH_SHORT).show();
                chat c = dataSnapshot.getValue(chat.class);
//                notificationRequest(c.getName(),c.getMessage(),c.getPicurl());
                notifyyyMessage(c.getName(), c.getMessage(), c.getPicurl(), c.getId());
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

        return START_STICKY;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void notifyyyMessage(final String name, final String mesage, final String picUrl, String id) {
        int num = (9999 - 1000) + 1;
//        Bitmap bitmap = loadBitmap(picUrl);
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.adnan.panachatfragment");
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        notificationManager.notify(num++,n);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setTicker("Office Work")
                .setContentTitle(name)
                .setContentText(mesage)

                .setTicker("New Message")
//                .setLargeIcon(R.drawable.user)
                .setSmallIcon(R.drawable.user)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{500, 500})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .build();

        notificationManager.notify(0, notification);

        fire.child("AppData").child("Notificationn").child("OneToOne").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void notificationRequest(String name, String message, String picUrl) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();



    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
