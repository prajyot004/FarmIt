package com.example.farmit;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class selladapter extends FirebaseRecyclerAdapter<sellmodel, selladapter.myviewholder> {


    public selladapter(@NonNull FirebaseRecyclerOptions<sellmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull sellmodel model) {
        holder.cost.setText("Cost :"+model.getCost());
        holder.location.setText("Location :"+model.getLocation());
        Glide.with(holder.si.getContext()).load(model.getPurl()).into(holder.si);
        holder.si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),itemDetail.class);
                intent.putExtra("addr",model.getAddr());
                intent.putExtra("cont",model.getContact());
                intent.putExtra("cost",model.getCost());
                intent.putExtra("location",model.getLocation());
                intent.putExtra("oname",model.getOname());
                intent.putExtra("pdesc",model.getPdesc());
                intent.putExtra("pname",model.getPname());
                intent.putExtra("purl",model.getPurl());
                String temp = getRef( holder.getAdapterPosition()).getKey();
                intent.putExtra("id",temp);
                System.out.println(temp);
                System.out.println(model.getAddr());
                view.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sell_row,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
    ImageView si;
    TextView cost, location;

    public myviewholder(@NonNull View itemView) {
        super(itemView);

        si = itemView.findViewById(R.id.simg);
        cost = itemView.findViewById(R.id.cost);
        location = itemView.findViewById(R.id.location);

    }
}
}
