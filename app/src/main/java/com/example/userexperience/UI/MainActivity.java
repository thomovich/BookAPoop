package com.example.userexperience.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.userexperience.Fragments.CreateNewRentingFragment;

import com.example.userexperience.Fragments.FireListFragment;

import com.example.userexperience.Fragments.MapFragment;
import com.example.userexperience.Fragments.MainFragment;
import com.example.userexperience.R;



import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private MainActivityViewModel viewModel;
    private ListViewModel model;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        checkIfSignedIn();
        openFragment("Main");
        setContentView(R.layout.activity_main);
        model = new ViewModelProvider(this).get(ListViewModel.class);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED  ){
            requestPermissions(new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return ;
        }
        model.init(this);
        fab = findViewById(R.id.fab);
        coordinatorLayout = findViewById(R.id.clayout);

        //Sætter toolbar
        toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nvView);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                navigationView.bringToFront();
                drawerLayout.requestLayout();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        fab.setOnClickListener(v->{
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Remember to wash your hands", Snackbar.LENGTH_SHORT);
            snackbar.show();
        });


    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.first_frag) {
            openFragment("Main");
        } else if (id == R.id.second_frag) {
            openFragment("List");
        } else if (id == R.id.third_frag) {
            openFragment("Map");
        } else if (id == R.id.fourth_frag) {
            openFragment("Rent");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void checkIfSignedIn(){
        viewModel.getCurrentUser().observe(this, user ->{
            if(user != null){

            } else
                startLoginActivity();

        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, SigninActivity.class));
        finish();
    }

    public void signOut(MenuItem item){
        viewModel.signOut();
    }




    public void openFragment(String fragment){
        if(fragment.equals("Main")){
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, MainFragment.class, null).commit();
        } else if (fragment.equals("List")){
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, FireListFragment.class, null).commit();
        } else if (fragment.equals("Map")){
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, MapFragment.class, null).commit();
        } else if(fragment.equals("Rent")){
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, CreateNewRentingFragment.class, null).commit();
        }

    }

    @Override
    protected void onStart() {

        super.onStart();
    }
}