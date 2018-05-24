package com.example.jagin.infomovie;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.jagin.infomovie.fragments.FavoritesFragments;
import com.example.jagin.infomovie.fragments.PeliculasFragments;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

}
