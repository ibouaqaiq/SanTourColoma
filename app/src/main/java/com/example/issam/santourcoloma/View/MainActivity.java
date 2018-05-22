package com.example.issam.santourcoloma.View;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.issam.santourcoloma.Fragments.MapsFragment;
import com.example.issam.santourcoloma.Model.Sitio;
import com.example.issam.santourcoloma.Model.User;
import com.example.issam.santourcoloma.R;
import com.firebase.ui.auth.AuthUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;

    private TextView nameNav;
    private TextView correoNav;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //barra transparente
        Window w= getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR,WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR);
        }

        String uid = FirebaseAuth.getInstance().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //FirebaseUser user = firebaseAuth.getCurrentUser();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* Load user info in drawer header*/
        View header = navigationView.getHeaderView(0);
//        ImageView photo = header.findViewById(R.id.userPhoto);
        final TextView nameNav = header.findViewById(R.id.nameNav);
        final TextView correoNav = header.findViewById(R.id.correoNav);

        mDatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user != null) {
                    //        Glide.with(this)
//                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
//                .apply(new RequestOptions().circleCrop())
//                .into(photo);
                    nameNav.setText(user.displayName);
                    correoNav.setText(user.email);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());

            }
        });


        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, new MapsFragment()).commit();

        loadSitios();
    }

    void loadSitios(){
        String key = mDatabase.child("sitios/data").push().getKey();
        mDatabase.child("sitios/data").child(key).setValue(new Sitio("Ayunta",4,"el ayntamienttoooo", "45.45845345", "2.3244324"));
        mDatabase.child("sitios/all").child(key).setValue(true);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.item_map) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, new MapsFragment()).commit();
        }else if(id == R.id.item_sitios){
           Intent intent = new Intent(this, SitiosActivity.class);
           startActivity(intent);
        }else if (id == R.id.nav_signout) {
            signOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void signOut(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }
}


