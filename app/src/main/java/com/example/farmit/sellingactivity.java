package com.example.farmit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.tuann.floatingactionbuttonexpandable.FloatingActionButtonExpandable;

public class sellingactivity extends AppCompatActivity {

    RecyclerView recView;
    selladapter sadapter;
    FloatingActionButtonExpandable fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellingactivity);


        Toolbar toolbar = findViewById(R.id.sellingtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Selling Point");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recView = findViewById(R.id.sellingrec);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recView.setLayoutManager(gridLayoutManager);

        FirebaseRecyclerOptions<sellmodel> options =
                new FirebaseRecyclerOptions.Builder<sellmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("selling"), sellmodel.class)
                        .build();

        sadapter = new selladapter(options);
        recView.setAdapter(sadapter);

        fb = findViewById(R.id.fabsell);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),sellitem.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        sadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sadapter.stopListening();
    }
}