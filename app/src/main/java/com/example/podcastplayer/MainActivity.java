package com.example.podcastplayer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.podcastplayer.screens.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.podcastplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity /*implements NavigationView.OnNavigationItemSelectedListener*/ {
    public static final String LOG_KEY = "LOG_KEY@" + MainActivity.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private UserStorage userStorage;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.userStorage = ((App)this.getApplication()).getUserStorage();
        if (userStorage.hasToLogin()) {
            this.goToLogin();
            return;
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        this.navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_subscribe, R.id.nav_discover)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // https://stackoverflow.com/questions/59827803/how-to-set-navigationview-with-appbarconfiguration
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_subscribe:
                        navController.navigate(R.id.nav_subscribe);
                        break;
                    case R.id.nav_discover:
                        navController.navigate(R.id.nav_discover);
                        break;
                    case R.id.nav_logout: {
                        Log.d("LOG_KEY@" + this.getClass().getSimpleName(), "Wylogowano!");
                        break;
                    }
                }

                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //this.setNavigationViewListener();

        View headerView = this.navigationView.getHeaderView(0);
        TextView drawerNameTextView = headerView.findViewById(R.id.drawerNameTextView);
        TextView drawerEmailTextView = headerView.findViewById(R.id.drawerEmailTextView);

        drawerNameTextView.setText(this.userStorage.getFullName());
        drawerEmailTextView.setText(this.userStorage.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            this.userStorage.logout();
            this.goToLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void goToLogin() {
        this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    // https://stackoverflow.com/questions/42297381/onclick-event-in-navigation-drawer
    // Dzia??a dla akcji klikni??cia, ale przy zmianie fragmentu g??rny pasek si?? nie zmienia
    // Dzia??a za to rozwi??zanie navigationView.setNavigationItemSelectedListener w metodzie onCreate
    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_logout: {
                Log.d("LOG_KEY@" + this.getClass().getSimpleName(), "Wylogowano!");
                break;
            }
        }

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListener() {
        this.navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }*/
}