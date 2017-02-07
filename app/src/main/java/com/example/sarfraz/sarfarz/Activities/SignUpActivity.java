package com.example.sarfraz.sarfarz.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sarfraz.sarfarz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText fname,lname,password,email;
    DatabaseReference fire;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fname=(EditText)findViewById(R.id.editText);
        lname=(EditText)findViewById(R.id.editText2);
        email=(EditText)findViewById(R.id.editText4);
        password=(EditText)findViewById(R.id.editText3);
        submit=(Button) findViewById(R.id.button2);
        fire=FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
                }
                // ...
            }
        };
        submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        createAccount();
//        .child("HelloWorld").setValue("Adnan Ahmed");

    }
});

    }

    public void createAccount() {
        final String first=fname.getText().toString();
        final String last=lname.getText().toString();
        String pass=password.getText().toString();
        final String emaill=email.getText().toString();
        mAuth.createUserWithEmailAndPassword(emaill, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            Toast.makeText(SignUpActivity.this, "Failed"+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUpActivity.this, "Sucessful",
                                    Toast.LENGTH_SHORT).show();
                            Map<String ,String> map=new HashMap<String, String>();
                            map.put("name",first+" "+last);
                            map.put("picurl","N/A");
                            map.put("email",emaill);
                            map.put("status","N/A");
                            map.put("contact","N/A");
                            map.put("birthday","N/A");

        fire.child("AppData").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(map);
                        }

                        // ...
                    }
                });

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}