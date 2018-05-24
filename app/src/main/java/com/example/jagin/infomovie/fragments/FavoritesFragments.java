package com.example.jagin.infomovie.fragments;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jagin.infomovie.BuildConfig;
import com.example.jagin.infomovie.R;
import com.example.jagin.infomovie.db.FavoritesPeliculasDatabase;
import com.example.jagin.infomovie.model.Pelicula;

import java.util.List;


public class FavoritesFragments extends Fragment {
    private static FavoritesPeliculasDatabase db;
    private static List<Pelicula> favoritePeliculas;
    private static String favorita;
    private static TextView texto;

    public static FavoritesFragments newInstance(){
        return new FavoritesFragments();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites_fragments, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        texto = getView().findViewById(R.id.tv_fragmentFavoritos);
        getFavoritePeliculas();
        favorita = "";
    }

    private void getFavoritePeliculas(){
        db = Room.databaseBuilder(getActivity(), FavoritesPeliculasDatabase.class, BuildConfig.DB_NAME).build();
        GetFavoritesTask getFavoritesTask = new GetFavoritesTask();
        getFavoritesTask.execute();
    }

    private static class GetFavoritesTask extends AsyncTask<Void, Integer, List<Pelicula>> {

        @Override
        protected List<Pelicula> doInBackground(Void... voids) {
            return db.favoritesPeliculasDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Pelicula> peliculas) {
            super.onPostExecute(peliculas);
            favoritePeliculas = peliculas;
            for (int i = 0; i < favoritePeliculas.size(); i++) {
                favorita = favorita + ", " + favoritePeliculas.get(i).title;

            }
            texto.setText(favorita);
        }
    }
}
