package com.example.farmit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class createjob extends AppCompatActivity {

    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    String umailfirebase;
    Bitmap bitmap;
    Uri filepath;
    EditText ContactNumberet,Jobtitleet,jobdateet,jobberNameet,jobdesc;
    ImageView im;
    Button browse,submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createjob);
        Toolbar toolbar = findViewById(R.id.jobactionbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Job");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ContactNumberet = findViewById(R.id.ContactNumberet);
        Jobtitleet = findViewById(R.id.Jobtitleet);
        jobdateet = findViewById(R.id.jobdateet);
        jobberNameet = findViewById(R.id.jobberNameet);
        jobdesc = findViewById(R.id.jobdesc);
        browse = findViewById(R.id.browse1);
        submit = findViewById(R.id.submit2);
        im = findViewById(R.id.imageView2);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        umailfirebase = user.getEmail();
        umailfirebase = umailfirebase.replace(".",",");

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(createjob.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContactNumberet.getText().toString().isEmpty()){
                    ContactNumberet.setError("Enter the number");
                    return;
                }else if(Jobtitleet.getText().toString().isEmpty()){
                    Jobtitleet.setError("Enter the job title");
                    return;
                }else if(jobdateet.getText().toString().isEmpty()){
                    jobdateet.setError("Enter the job date");
                    return;
                }else if(jobberNameet.getText().toString().isEmpty()){
                    jobberNameet.setError("Enter the your name");
                    return;
                }else if(jobdesc.getText().toString().isEmpty()){
                    jobdesc.setError("Enter the job location");
                    return;
                }
                else if(filepath == null){
                    Toast.makeText(createjob.this,"select image",Toast.LENGTH_SHORT).show();
                    return;
                }
                uploadtofirebase();
            }
        });
    }

    private void uploadtofirebase() {
        String name,date,desc,title,cnum;
        name = jobberNameet.getText().toString();
        date = jobdateet.getText().toString();
        desc = jobdesc.getText().toString();
        title = Jobtitleet.getText().toString();
        cnum = ContactNumberet.getText().toString();
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File uploader");
        dialog.show();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference("Image"+new Random().nextInt(1000));
        uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                dialog.dismiss();
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference root = db.getReference("jobs");
                                dataholderjob obj = new dataholderjob(cnum,date,uri.toString()+"",name,title,desc);
                                root.child(umailfirebase+""+new Random().nextInt(1000000)).setValue(obj);
                                startActivity(new Intent(getParentActivityIntent()));
                                finish();
                            }
                        });

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded "+(int)percent+" %");
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK){
            filepath = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                im.setImageBitmap(bitmap);
            }catch (Exception e){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}