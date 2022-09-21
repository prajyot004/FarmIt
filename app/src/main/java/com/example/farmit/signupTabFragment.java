package com.example.farmit;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signupTabFragment extends Fragment {

   EditText email,pass,cpass,mob;
   Button btn;
   TabLayout tabLayout;
   boolean passwordvisible1,passwordvisible2;
   DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farmit-933ff-default-rtdb.firebaseio.com/");
   FirebaseAuth mAuth;
   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);

      email = root.findViewById(R.id.email);
      pass = root.findViewById(R.id.password);
      cpass = root.findViewById(R.id.confirm_pass);
      mob = root.findViewById(R.id.mobile);
      btn = root.findViewById(R.id.signup);
      tabLayout = root.findViewById(R.id.tab_layout);
      mAuth = FirebaseAuth.getInstance();


      pass.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View view, MotionEvent event) {
            final int right = 2;
            if(event.getAction()==MotionEvent.ACTION_UP){
               if(event.getRawX()>=pass.getRight()-pass.getCompoundDrawables()[right].getBounds().width()){
                  int selection = pass.getSelectionEnd();
                  if(passwordvisible1){
                     pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                     pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                     passwordvisible1=false;
                  }else{
                     pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                     pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                     passwordvisible1=true;

                  }
                  pass.setSelection(selection);
                  return  true;
               }
            }
            return false;
         }
      });

      cpass.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View view, MotionEvent event) {
            final int right = 2;
            if(event.getAction()==MotionEvent.ACTION_UP){
               if(event.getRawX()>=cpass.getRight()-cpass.getCompoundDrawables()[right].getBounds().width()){
                  int selection = cpass.getSelectionEnd();
                  if(passwordvisible2){
                     cpass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                     cpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                     passwordvisible2=false;
                  }else{
                     cpass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                     cpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                     passwordvisible2=true;

                  }
                  cpass.setSelection(selection);
                  return  true;
               }
            }
            return false;
         }
      });

      btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            String emailtxt = email.getText().toString();
            String passtxt = pass.getText().toString();
            String cpasstxt = cpass.getText().toString();
            String mobtxt = mob.getText().toString();

            if(emailtxt.isEmpty()){
               email.setError("Enter the Email");
               return;
            }else if(!emailtxt.contains("@") || !emailtxt.contains(".")){
               email.setError("Enter the correct Email");
               return;
            }else if(passtxt.isEmpty()){
               pass.setError("Enter the Password");
               return;
            }else if(passtxt.length() <8){
               pass.setError("Password size must be more than 8");
               return;
            }else if(!cpasstxt.equals(passtxt)){
               cpass.setError("Password not matched");
               return;
            }else if(cpasstxt.isEmpty()){
               cpass.setError("Confirm password");
               return;
            }

            if(mobtxt.isEmpty()){
               mob.setError("Enter mobile no.");
            }else if(mobtxt.length()<10){
               mob.setError("Enter 10 digit number");
               return;
            }
            createuser(emailtxt,passtxt);
//            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//               @Override
//               public void onDataChange(@NonNull DataSnapshot snapshot) {
//                  if(snapshot.hasChild(mobtxt)){
//                     Toast.makeText(getContext(),"user present",Toast.LENGTH_SHORT).show();
//                  }
//                  else{
//                     databaseReference.child("users").child(mobtxt).child("uname").setValue(emailtxt);
//                     databaseReference.child("users").child(mobtxt).child("password").setValue(passtxt);
//                     Toast.makeText(getContext(),"registered",Toast.LENGTH_SHORT).show();
//
//                  }
//               }
//
//               @Override
//               public void onCancelled(@NonNull DatabaseError error) {
//
//               }
//            });

         }
      });

      return root;
   }

   private void createuser(String email,String pass){

      mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
               Toast.makeText(getContext(),"Registered ",Toast.LENGTH_SHORT).show();

            }else{
               Toast.makeText(getContext(),"Registration Failed "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
            }
         }
      });
   }

}
