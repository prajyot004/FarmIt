package com.example.farmit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class jobAdapter extends FirebaseRecyclerAdapter<jobmodel,jobAdapter.myviewholder> {

    FirebaseAuth mAuth;
    String umailfirebase;
    Context context;
    public jobAdapter(@NonNull FirebaseRecyclerOptions<jobmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull jobmodel model) {
        holder.jobbername.setText(model.getName());
        holder.contno.setText(model.getContact());
        holder.jobtitle.setText("Job Title: "+model.getTitle());
        holder.jobdate.setText(model.getDate());
        Glide.with(holder.img1.getContext()).load(model.getImg()).into(holder.img1);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),jobdesc.class);
                intent.putExtra("imgname",model.getImg());
                intent.putExtra("title",model.getTitle());
                intent.putExtra("desc",model.getZdescription());
                intent.putExtra("name",model.getName());
                intent.putExtra("date",model.getDate());
                intent.putExtra("contact",model.getContact());
                view.getContext().startActivity(intent);
            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(umailfirebase);
                String temp =FirebaseDatabase.getInstance().getReference().child("jobs")
                        .child(getRef(holder.getAdapterPosition()).getKey()).getKey();
                System.out.println(temp);
                if(temp.contains(umailfirebase)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.jobbername.getContext());
                builder.setTitle("Are you sure ?");
                builder.setMessage("Deleted data Can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("jobs")
                                .child(getRef( holder.getAdapterPosition()).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.nametext.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
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

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        umailfirebase = user.getEmail();
        umailfirebase = umailfirebase.replace(".",",");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_row,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        ImageView img1,del;
        TextView contno,jobtitle,jobdate,jobbername,tvc,Date,nametext;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img1 =(ImageView) itemView.findViewById(R.id.jrimg);
            tvc = itemView.findViewById(R.id.tvc);
            Date = itemView.findViewById(R.id.Date);
            nametext = itemView.findViewById(R.id.nametext);
            contno =itemView.findViewById(R.id.ContactNo);
            jobtitle = itemView.findViewById(R.id.jobTitle);
            jobdate = itemView.findViewById(R.id.jobdate);
            jobbername=itemView.findViewById(R.id.JobberName);
            del = itemView.findViewById(R.id.del_imgjr);
        }
    }
}
