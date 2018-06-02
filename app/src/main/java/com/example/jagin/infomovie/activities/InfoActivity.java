package com.example.jagin.infomovie.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.jagin.infomovie.InfoFragments;
import com.example.jagin.infomovie.R;
import com.example.jagin.infomovie.db.PreferencesManager;
import com.example.jagin.infomovie.servicios.MediaService;

import java.util.Objects;


public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Fragment infoFragments = InfoFragments.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.fl_ContenedorInfo, infoFragments);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();



    }

    @Override
    protected void onPause() {
        stopService(new Intent(this, MediaService.class));
        super.onPause();
    }

    @Override
    protected void onResume() {
        PreferencesManager preferencesManager = new PreferencesManager(this);
        if (preferencesManager.isMusicEnabled()) {
            startService(new Intent(this, MediaService.class));
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, MediaService.class));

        super.onDestroy();
    }
}
