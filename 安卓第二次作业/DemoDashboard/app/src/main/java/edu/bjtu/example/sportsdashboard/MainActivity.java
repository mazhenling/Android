package edu.bjtu.example.sportsdashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
//import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import static android.support.v4.view.GravityCompat.*;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    //set default xml
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    new IndexFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }
//control toolbar
    @SuppressLint("ResourceType")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new IndexFragment()).commit();

                break;
            case R.id.nav_course:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new MycourseFragment()).commit();
                break;
            case R.id.nav_invoice:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new MyinvoiceFragment()).commit();
                break;
            case R.id.nav_coachlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new CoachListFragment()).commit();
                break;

            case R.id.nav_attendance:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new MyattendenceFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new SettingFragment()).commit();
                break;
            case R.id.nav_myaccount:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new LoginFragment()).commit();
                break;
        }

        drawer.closeDrawer(START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(START)) {
            drawer.closeDrawer(START);
        } else {
            super.onBackPressed();
        }
    }
}
