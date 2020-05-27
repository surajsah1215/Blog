
package com.example.blog.Activites;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.blog.Model.Bolg;
        import com.example.blog.R;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class AddpostActivity extends AppCompatActivity {

    private ImageButton  mpostImage;
    private EditText mPostTitle;
    private EditText mpostDesc;
    private Button mSubmitButton;
    private DatabaseReference mpostdatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialog mprogress;
    private Uri mImageUri;
    private static final int Gallery_Code = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mpostdatabase = FirebaseDatabase.getInstance().getReference().child("Mlog");

        mpostImage = (ImageButton)findViewById(R.id.imageView);
        mPostTitle = (EditText)findViewById(R.id.editText);
        mpostDesc = (EditText) findViewById(R.id.editText2);
        mSubmitButton = (Button)findViewById(R.id.button);

        mpostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Code);
            }
        });


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //posting to our database
                startposting();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Code && resultCode == RESULT_OK){
            mImageUri = data.getData();
            mpostImage.setImageURI(mImageUri);
        }
    }

    private void startposting() {
        mprogress.setMessage("posting to blog");
        mprogress.show();


        String titleval = mPostTitle.getText().toString().trim();
        String descval = mpostDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(titleval) &&  !TextUtils.isEmpty(descval)){
            //start uploading

            Bolg bolg = new Bolg("Title","description","imageurl"
                    ,"timestamp", "userid");

            mpostdatabase.setValue(bolg).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"Item added"
                            ,Toast.LENGTH_LONG).show();
                    mprogress.dismiss();
                }
            });
        }


    }


}
