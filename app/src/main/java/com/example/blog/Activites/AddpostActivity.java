
package com.example.blog.Activites;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.annotation.SuppressLint;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.icu.text.CaseMap;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.storage.StorageManager;
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
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;

        import java.util.HashMap;
        import java.util.Map;
        import java.util.concurrent.ScheduledExecutorService;

public class AddpostActivity extends AppCompatActivity {

    private ImageButton  mpostImage;
    private EditText mPostTitle;
    private EditText mpostDesc;
    private Button mSubmitButton;
    private DatabaseReference mpostdatabase;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    public ProgressDialog mprogress;
    private Uri mImageUri;
    private static final int Gallery_Code = 1;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();

        mpostdatabase = FirebaseDatabase.getInstance().getReference().child("Mlog");

        mprogress = new ProgressDialog(this);

        mpostImage = (ImageButton)findViewById(R.id.imageButton);
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


        final String titleval = mPostTitle.getText().toString().trim();
        final String descval = mpostDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(titleval) &&  !TextUtils.isEmpty(descval)
          && mImageUri != null){
            //start uploading
            mprogress.setMessage("posting");
            mprogress.show();

            StorageReference filepath = mStorage.child("MBlog_images")
                    .child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadurl = taskSnapshot.getStorage().getDownloadUrl();

                    DatabaseReference newPost = mpostdatabase.push();

                    Map<String, String> dataTosave = new HashMap<>();
                    dataTosave.put("Title", titleval);
                    dataTosave.put("desc", descval);
                    dataTosave.put("Images",downloadurl.toString());
                    dataTosave.put("TimeStamp",String.valueOf(System.currentTimeMillis()));
                    dataTosave.put("UserId",mUser.getUid());

                    newPost.setValue(dataTosave);

                    mprogress.dismiss();

                    startActivity(new Intent(AddpostActivity.this, postlistActivity.class));
                    finish();
                }
            });



        }


    }


}
