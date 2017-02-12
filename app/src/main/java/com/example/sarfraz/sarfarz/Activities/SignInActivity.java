package com.example.sarfraz.sarfarz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sarfraz.sarfarz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button submit;
    TextView signUp;
    private FirebaseAuth mAuth;
    ProgressDialog pd;
    //    EmailPasswordActivity.java
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        pd.setMessage("Signing In..");
        pd.setTitle("Plz Wait..");
        if (NavDrawerActivity.context != null) {
            NavDrawerActivity.context.finish();
        } else {
            Log.d("", "");
        }
        email = (EditText) findViewById(R.id.signInEmail);
        password = (EditText) findViewById(R.id.signInPassword);
        signUp = (TextView) findViewById(R.id.notAccount);
        submit = (Button) findViewById(R.id.signInButton);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent i = new Intent(SignInActivity.this, NavDrawerActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    // User is signed out

                }
                // ...
            }
        };


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pd.show();
                if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(SignInActivity.this, "Plz Complete All Text Fields", Toast.LENGTH_SHORT).show();
               pd.dismiss();
                } else {
                    signIn(email.getText().toString(), password.getText().toString());

                }
            }
        });
    }

    public void signIn(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            pd.dismiss();
                            Toast.makeText(SignInActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            pd.dismiss();
                            Toast.makeText(SignInActivity.this, "Sucessfull",
                                    Toast.LENGTH_SHORT).show();
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
