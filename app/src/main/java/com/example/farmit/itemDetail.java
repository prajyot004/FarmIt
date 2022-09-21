package com.example.farmit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class itemDetail extends AppCompatActivity {

    TextView addr,contact,cost,location,oname,pdesc,pname;
    ImageView dp,del;
    FirebaseAuth mAuth;
    String umailfirebase;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Toolbar toolbar = findViewById(R.id.toolbar41);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addr = findViewById(R.id.tv36);
        contact = findViewById(R.id.tv34);
        cost = findViewById(R.id.tv31);
        location = findViewById(R.id.tv32);
        oname = findViewById(R.id.tv33);
        pdesc = findViewById(R.id.tv35);
        pname = findViewById(R.id.tv30);
        dp = findViewById(R.id.iimg);
        del = findViewById(R.id.selldel);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        umailfirebase = user.getEmail();
        umailfirebase = umailfirebase.replace(".", ",");

        String im = getIntent().getStringExtra("purl");
        Glide.with(dp.getContext()).load(im).into(dp);
        addr.setText("Owner's Address : " +getIntent().getStringExtra("addr"));
        contact.setText("Owner's Contact : " +getIntent().getStringExtra("cont"));
        cost.setText("Product cost : "+getIntent().getStringExtra("cost"));
        location.setText("Product Location : "+getIntent().getStringExtra("location"));
        oname.setText("Owner's Name : "+getIntent().getStringExtra("oname"));
        pdesc.setText("Product Description : "+getIntent().getStringExtra("pdesc"));
        pname.setText("Product Name : " +getIntent().getStringExtra("pname"));


        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(umailfirebase);
                String temp = getIntent().getStringExtra("id");
                System.out.println(temp + "temp ");
                if(temp.contains(umailfirebase)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemDetail.this);
                    builder.setTitle("Are you sure ?");
                    builder.setMessage("Deleted data Can't be undo");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseDatabase.getInstance().getReference().child("selling")
                                    .child(temp+"").removeValue();
                            startActivity(getParentActivityIntent());
                            finish();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();

                }else{
                    Toast.makeText(view.getContext(),"Sorry, You can't Delete",Toast.LENGTH_SHORT).show();
                }
//
            }
        });

    }
}