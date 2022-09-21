package com.example.farmit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class jobdesc extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4,tv5;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobdesc);

        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.imageview5);
        tv1 = findViewById(R.id.textView7);
        tv2 = findViewById(R.id.textView9);
        tv3= findViewById(R.id.textView11);
        tv4 = findViewById(R.id.textview13);
        tv5 = findViewById(R.id.textview15);

        String im = getIntent().getStringExtra("imgname");
        Glide.with(imageView.getContext()).load(im).into(imageView);
        tv1.setText(getIntent().getStringExtra("name"));
        tv2.setText(getIntent().getStringExtra("title"));
        tv3.setText(getIntent().getStringExtra("desc"));
        tv4.setText(getIntent().getStringExtra("contact"));
        tv5.setText(getIntent().getStringExtra("date"));

    }
}