package com.example.farmit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.tuann.floatingactionbuttonexpandable.FloatingActionButtonExpandable;

public class jobactivity extends AppCompatActivity {

    RecyclerView recview;
    FloatingActionButtonExpandable fb;
    jobAdapter jobadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobactivity);

        Toolbar toolbar = findViewById(R.id.jobactionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("JOB");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recview = findViewById(R.id.jobrecview);
        fb = findViewById(R.id.fab);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(jobactivity.this,createjob.class);
                startActivity(intent);
                finish();
            }
        });

        recview.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<jobmodel> options =
                new FirebaseRecyclerOptions.Builder<jobmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("jobs"), jobmodel.class)
                        .build();
        jobadapter = new jobAdapter(options);
        recview.setAdapter(jobadapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        jobadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        jobadapter.stopListening();
    }
}