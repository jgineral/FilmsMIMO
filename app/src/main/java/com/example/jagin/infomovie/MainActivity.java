package com.example.jagin.infomovie;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.jagin.infomovie.fragments.FavoritesFragments;
import com.example.jagin.infomovie.fragments.PeliculasFragments;


public class MainActivity extends AppCompatActivity {

    private  static Activity mActivity;


    public  static Activity getmActivity() {
        return mActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startService(new Intent(MainActivity.this, MediaService.class));
        mActivity = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_Contenedor, fragmentClicked);
        //fragmentTransaction.addToBackStack("lista"); //Guardar el fragments
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

    @Override
    protected void onDestroy() {
        //stopService(new Intent(MainActivity.this, MediaService.class));
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
