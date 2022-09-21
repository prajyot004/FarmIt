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

public class sellitem extends AppCompatActivity {

    EditText cost, location, contact, pName, pDesc, add, oName;
    Button browse, submit;
    ImageView dp;
    FirebaseAuth mAuth;
    String umailfirebase;
    Bitmap bitmap;
    Uri filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellitem);

        Toolbar toolbar = findViewById(R.id.sellitemab);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sell Something");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cost = findViewById(R.id.pcost);
        pName = findViewById(R.id.productName);
        location = findViewById(R.id.plocation);
        oName = findViewById(R.id.ownerName);
        contact = findViewById(R.id.ocontact);
        add = findViewById(R.id.address);
        pDesc = findViewById(R.id.description);
        dp = findViewById(R.id.imageView21);
        browse = findViewById(R.id.browse11);
        submit = findViewById(R.id.submit21);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        umailfirebase = user.getEmail();
        umailfirebase = umailfirebase.replace(".", ",");

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(sellitem.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image File"), 1);
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
                if (cost.getText().toString().isEmpty()) {
                    cost.setError("Enter cost");
                    return;
                } else if (pName.getText().toString().isEmpty()) {
                    pName.setError("Enter Product Name");
                    return;
                } else if (location.getText().toString().isEmpty()) {
                    location.setError("Enter location");
                    return;
                } else if (oName.getText().toString().isEmpty()) {
                    oName.setError("Enter Owner Name");
                    return;
                } else if (contact.getText().toString().isEmpty()) {
                    contact.setError("Enter contact Number");
                    return;
                } else if (add.getText().toString().isEmpty()) {
                    add.setError("Enter Address");
                    return;
                } else if (pDesc.getText().toString().isEmpty()) {
                    pDesc.setError("Enter Product Description");
                    return;
                }else if(filepath == null){
                    Toast.makeText(sellitem.this,"select image",Toast.LENGTH_SHORT).show();
                    return;
                }

                uploadtofirebase();
            }
        });
    }

    private void uploadtofirebase() {
        String oname1,pname1,location1,cost1,desc1,contact1,add1;
        oname1 = oName.getText().toString();
        pname1 = pName.getText().toString();
        location1 = location.getText().toString();
        cost1 = cost.getText().toString();
        desc1 = pDesc.getText().toString();
        contact1 = contact.getText().toString();
        add1 = add.getText().toString();

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File uploader");
        dialog.show();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference("Image"+new Random().nextInt(100000));
        uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dialog.dismiss();
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference root = db.getReference("selling");
                        dataholdersell obj = new dataholdersell(add1,contact1,cost1,location1,oname1,desc1,pname1,uri.toString()+"");
                        root.child(umailfirebase+""+new Random().nextInt(1000000)).setValue(obj);
                        startActivity(new Intent(getParentActivityIntent()));
                        finish();
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                dialog.setMessage("Uploaded "+(int)percent+" %");
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                dp.setImageBitmap(bitmap);
            } catch (Exception e) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}