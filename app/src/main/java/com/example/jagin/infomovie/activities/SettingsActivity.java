package com.example.jagin.infomovie.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.jagin.infomovie.R;
import com.example.jagin.infomovie.fragments.InfoFragments;
import com.example.jagin.infomovie.fragments.SettingsFragments;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Fragment settingsFragments = SettingsFragments.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.fl_ContenedorSettings, settingsFragments);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


}
