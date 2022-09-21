package com.example.farmit;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    NavigationView naview;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    FirebaseAuth mAuth;
    TextView name,email;
    Fragment temp;

    boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        name = findViewById(R.id.uname);
        email = findViewById(R.id.uemail);

//        firebase linking
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

//        google linking
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        naview = (NavigationView) findViewById(R.id.nav_menu);
        updateprof(acct,user);
        drawerLayout = findViewById(R.id.drawer);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new homeFragment()).commit();
        naview.setCheckedItem(R.id.home);

        naview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        temp = new homeFragment();
                        break;
                    case R.id.userprofile:
                        Intent intent = new Intent(home.this,profileactivity.class);
                        startActivity(intent);
                        break;
                    case R.id.job:
                        Intent intent2 = new Intent(home.this,jobactivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.sellingpoing:
                        Intent intent3 = new Intent(home.this,sellingactivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.logout:
                        mAuth.signOut();
                        signOut();
                        flag = false;
                        break;
                }
                if(flag == false){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    flag = true;
                    return true;
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }


    public void updateprof(GoogleSignInAccount acct,FirebaseUser user){
        naview = (NavigationView) findViewById(R.id.nav_menu);
        View headerview = naview.getHeaderView(0);
        TextView name1 = headerview.findViewById(R.id.uname);
        TextView email1 = headerview.findViewById(R.id.uemail);


        if(acct!= null){
            name1.setText(acct.getDisplayName());
            email1.setText(acct.getEmail());
            Uri photo = acct.getPhotoUrl();
            ImageView pic = (ImageView) headerview.findViewById(R.id.profilepicuser);
            Glide.with(this).load(acct.getPhotoUrl()).into(pic);
        }else{
            name1.setText(user.getDisplayName());
            email1.setText(user.getEmail());
        }

    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

}