package com.example.jagin.infomovie.asyncTask;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.example.jagin.infomovie.adapter.PeliculaAdapter;
import com.example.jagin.infomovie.db.FavoritesPeliculasDatabase;
import com.example.jagin.infomovie.model.Pelicula;

import java.util.List;

public class GetFavoritesTask extends AsyncTask<Void, Integer, List<Pelicula>> {
    private FavoritesPeliculasDatabase db;
    private List<Pelicula> favoritePeliculas;
    private PeliculaAdapter adapter;
    private RecyclerView rvFavoritos;

    public GetFavoritesTask(FavoritesPeliculasDatabase db, List<Pelicula> favoritePeliculas, PeliculaAdapter adapter, RecyclerView rvFavoritos) {
        this.db = db;
        this.favoritePeliculas = favoritePeliculas;
        this.adapter = adapter;
        this.rvFavoritos = rvFavoritos;
    }

    @Override
    protected List<Pelicula> doInBackground(Void... voids) {
        return db.favoritesPeliculasDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Pelicula> peliculas) {
        super.onPostExecute(peliculas);
        favoritePeliculas = peliculas;
        adapter.setData(favoritePeliculas);
        rvFavoritos.setAdapter(adapter);
    }
}