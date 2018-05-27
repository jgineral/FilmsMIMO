package com.example.jagin.infomovie.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jagin.infomovie.BuildConfig;
import com.example.jagin.infomovie.R;
import com.example.jagin.infomovie.model.Pelicula;
import com.squareup.picasso.Picasso;

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

        pelicula = getIntent().getParcelableExtra("pelicula");

        init(pelicula);

    }

    private void init(Pelicula pelicula)
    {

        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView ivBackdrop = findViewById(R.id.ivBackdrop);
        TextView tvAverage = findViewById(R.id.tvAverage);
        TextView tvDate = findViewById(R.id.tvdate);
        TextView tvAdult = findViewById(R.id.tvAdult);
        TextView tvOverview = findViewById(R.id.tvOverview);
        ImageView ivFavorite = findViewById(R.id.ivFavorite);


        tvTitle.setText(pelicula.getTitle());
        imagen = BuildConfig.URL_IMG + pelicula.getBackdrop_path();
        Picasso.with(this).load(imagen).into(ivBackdrop);
        media_votos = Double.toString(pelicula.getVote_average());
        tvAverage.setText(media_votos);
        tvDate.setText(pelicula.getRelease_date());
        if(pelicula.isFavorite()){
            ivFavorite.setImageResource(R.drawable.star_on);
        }
        else{
            ivFavorite.setImageResource(R.drawable.star_off);
        }
        if(pelicula.isAdult()){
            tvAdult.setText(R.string.adulto_true);
        }else{
            tvAdult.setText(R.string.all_public);
        }
        tvOverview.setText(pelicula.getOverview());

    }

}
