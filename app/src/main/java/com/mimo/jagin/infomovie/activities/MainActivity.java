package com.mimo.jagin.infomovie.activities;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.mimo.jagin.infomovie.R;
import com.mimo.jagin.infomovie.fragments.FavoritesFragments;
import com.mimo.jagin.infomovie.fragments.PeliculasFragments;
import com.mimo.jagin.infomovie.servicios.MediaService;


public class MainActivity extends AppCompatActivity {

    private  static Activity mActivity;

    public  static Activity getmActivity() {
        return mActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mActivity = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Recargar.
                        swipeRefreshLayout.setRefreshing(false);
                        Fragment fragmentToReset = getFragmentManager().findFragmentById(R.id.fl_Contenedor);
                        FragmentTransaction fragTransaction =   getFragmentManager().beginTransaction();
                        fragTransaction.detach(fragmentToReset).attach(fragmentToReset).commit();
                    }
                },1500);
            }
        });

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener);
        navigationView.setSelectedItemId(R.id.navigation_home);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener myOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectFragment(item);
            return true;
        }
    };



    private void selectFragment(MenuItem item) {
        Fragment fragmentClicked = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragmentClicked = PeliculasFragments.newInstance();
                break;
            case R.id.navigation_favorites:
                fragmentClicked = FavoritesFragments.newInstance();
                break;
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.fl_Contenedor, fragmentClicked);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(MainActivity.this, MediaService.class));
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ( item.getItemId() )
        {
            case R.id.action_info:
                Intent intent1 = new Intent();
                intent1.setClass(this, InfoActivity.class);
                startActivity(intent1);
                return true;

            case R.id.action_settings:
                Intent intent2 = new Intent();
                intent2.setClass(this, SettingsActivity.class);
                startActivity(intent2);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
