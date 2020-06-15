package com.example.blog.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.blog.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText password;
    private Button createaccount;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog mprogressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("Muser");
        mAuth = FirebaseAuth.getInstance();

        mprogressDialog = new ProgressDialog(this);

        firstname = (EditText)findViewById(R.id.FirstnameAct);
        lastname = (EditText)findViewById(R.id.LastnameAct);
        email = (EditText)findViewById(R.id.EmailAct);
        password = (EditText)findViewById(R.id.PasswordAct);
        createaccount = (Button)findViewById(R.id.CreateAccountAct);

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
        final String name = firstname.getText().toString().trim();
        final String lname = lastname.getText().toString().trim();
         String em = email.getText().toString().trim();
         String pwd = password.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lname)
            && !TextUtils.isEmpty(em) && !TextUtils.isEmpty(pwd)){

            mprogressDialog.setMessage("creating Account....");
            mprogressDialog.show();



            mAuth.createUserWithEmailAndPassword(em,pwd)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    if (authResult !=null){
                        String userid = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserDb = mDatabaseReference.child(userid);
                        currentUserDb.child("firstname").setValue(name);
                        currentUserDb.child("lastname").setValue(lname);
                        currentUserDb.child("image").setValue("none");

                        mprogressDialog.dismiss();
                        //send users to postlist

                        Intent intent = new Intent(CreateAccount.this,postlistActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                }
            });
        }
    }


}