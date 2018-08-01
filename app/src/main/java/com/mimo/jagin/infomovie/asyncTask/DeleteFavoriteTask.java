package com.mimo.jagin.infomovie.asyncTask;

import android.os.AsyncTask;

import com.mimo.jagin.infomovie.db.FavoritesPeliculasDatabase;
import com.mimo.jagin.infomovie.model.Pelicula;

public class DeleteFavoriteTask extends AsyncTask<Pelicula,Void,Void> {
    private FavoritesPeliculasDatabase db;

    public DeleteFavoriteTask(FavoritesPeliculasDatabase db) {
        this.db = db;
    }

    @Override
    protected Void doInBackground(Pelicula... peliculas) {
        db.favoritesPeliculasDao().delete(peliculas[0]);
        return null;
    }

}