package com.mimo.jagin.infomovie.asyncTask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mimo.jagin.infomovie.activities.PeliculaActivity;
import com.mimo.jagin.infomovie.adapter.PeliculaAdapter;
import com.mimo.jagin.infomovie.db.FavoritesPeliculasDatabase;
import com.mimo.jagin.infomovie.model.Pelicula;

import java.util.List;

public class GetFavoritesTask extends AsyncTask<Void, Integer, List<Pelicula>> {
    private FavoritesPeliculasDatabase db;
    private PeliculaAdapter adapter;
    private RecyclerView rvFavoritos;
    private Activity actividad;

    public GetFavoritesTask(FavoritesPeliculasDatabase db, PeliculaAdapter adapter, RecyclerView rvFavoritos, Activity actividad) {
        this.db = db;
        this.adapter = adapter;
        this.rvFavoritos = rvFavoritos;
        this.actividad = actividad;
    }

    @Override
    protected List<Pelicula> doInBackground(Void... voids) {
        return db.favoritesPeliculasDao().getAll();
    }

    @Override
    protected void onPostExecute(final List<Pelicula> peliculas) {
        super.onPostExecute(peliculas);

        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pelicula pelicula = peliculas.get(rvFavoritos.getChildAdapterPosition(v));
                Intent myIntent = new Intent();
                myIntent.setClass(actividad, PeliculaActivity.class);
                myIntent.putExtra("pelicula", pelicula);
                actividad.startActivity(myIntent);

            }
        });

        adapter.setData(peliculas);
        rvFavoritos.setAdapter(adapter);
        db.close();
    }
}