package com.example.jagin.infomovie.asyncTask;

import android.os.AsyncTask;


import com.example.jagin.infomovie.db.FavoritesPeliculasDatabase;
import com.example.jagin.infomovie.model.Pelicula;

public class InsertFavoriteTask extends AsyncTask<Pelicula, Void, Void> {
    private FavoritesPeliculasDatabase db;


    public InsertFavoriteTask(FavoritesPeliculasDatabase db) {
        this.db = db;
    }

    @Override
    protected Void doInBackground(Pelicula... peliculas) {
        db.favoritesPeliculasDao().insert(peliculas[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
