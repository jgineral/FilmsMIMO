package com.example.jagin.infomovie.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.example.jagin.infomovie.R;
import com.example.jagin.infomovie.fragments.DetalleFragments;
import com.example.jagin.infomovie.model.Pelicula;

public class PeliculaActivity extends AppCompatActivity
{
    private Pelicula pelicula;
    private String media_votos;
    private String imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contenido_pelicula);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = DetalleFragments.newInstance();



        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_ContenedorDetalle, fragment);
        //fragmentTransaction.addToBackStack("lista"); //Guardar el fragments
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }



}
