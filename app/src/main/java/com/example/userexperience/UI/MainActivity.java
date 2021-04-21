package com.example.userexperience.UI;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.util.LogPrinter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.userexperience.R;
import com.example.userexperience.adapters.BookingsAdapter;
import com.example.userexperience.models.PlacesToBook;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookingsAdapter.OnListItemClickListener {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private CoordinatorLayout layout;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;
    private MainActivityViewModel viewModel;
    private ArrayList<PlacesToBook> places = new ArrayList<>();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        checkIfSignedIn();
        fab = findViewById(R.id.fab);
        layout = findViewById(R.id.clayout);



        //Sætter toolbar
        toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDrawer = (DrawerLayout) findViewById(R.id.mydrawer);
        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);



        fab.setOnClickListener(v->{
            Toast.makeText(getApplicationContext(),"Klikket på floater",Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(layout, "Snacky snacky", Snackbar.LENGTH_LONG);
            snackbar.show();
        });



        places.add(new PlacesToBook("Nice apartment",70037,"8000 århus C", 10, 5, 4));
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        BookingsAdapter bookingsAdapter = new BookingsAdapter(places, this);
        recyclerView.setAdapter(bookingsAdapter);




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void checkIfSignedIn(){
        viewModel.getCurrentUser().observe(this, user ->{
            if(user != null){
                //Brugeren er logget ind
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

    @Override
    public void onListItemClick(int clickedItemIndex) {
        int listNumber = clickedItemIndex + 1;
        Toast.makeText(this, "List Number: " + listNumber + "is" + places.get(clickedItemIndex).getTitle(), Toast.LENGTH_SHORT).show();
    }


}