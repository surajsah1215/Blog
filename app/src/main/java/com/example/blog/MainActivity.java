package com.example.blog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.EOFException;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import org.w3c.dom.Text;


    public class MainActivity extends AppCompatActivity {
        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthlistner;
        private FirebaseUser mUser;
        private Button Sigin;
        private Button createAccunt;
        private EditText emailField;
        private EditText passwordField;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mAuth = FirebaseAuth.getInstance();
            Sigin = (Button) findViewById(R.id.signIN);
            createAccunt = (Button) findViewById(R.id.createAccID);
            emailField = (EditText) findViewById(R.id.EmailId);
            passwordField = (EditText) findViewById(R.id.passwordID);

            mAuthlistner = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    mUser = firebaseAuth.getCurrentUser();
                    if (mUser != null) {
                        Toast.makeText(MainActivity.this, "User signed in", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        Toast.makeText(MainActivity.this, "User signed Out", Toast.LENGTH_LONG)
                                .show();
                    }


                }
            };

            Sigin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(emailField.getText().toString())
                            && !TextUtils.isEmpty(passwordField.getText().toString())) ;
                    {

                        String email = emailField.getText().toString();
                        String pwd = passwordField.getText().toString();

                        login(email, pwd);
                    }

                }
            });

        }

        private void login(String email, String pwd) {

                mAuth.signInWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Signed in", Toast.LENGTH_LONG).show();
                                } else {
                                    //not yet
                                }
                            }
                        });


        }

        


        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        protected void onStart() {
            super.onStart();
            mAuth.addAuthStateListener(mAuthlistner);
        }

        @Override
        protected void onStop() {
            super.onStop();
            if (mAuthlistner != null) {
                mAuth.removeAuthStateListener(mAuthlistner);
            }
        }


    }
