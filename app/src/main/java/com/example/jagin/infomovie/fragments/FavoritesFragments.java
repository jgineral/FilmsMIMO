package com.example.jagin.infomovie.fragments;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jagin.infomovie.BuildConfig;
import com.example.jagin.infomovie.R;
import com.example.jagin.infomovie.adapter.PeliculaAdapter;
import com.example.jagin.infomovie.db.FavoritesPeliculasDatabase;
import com.example.jagin.infomovie.model.Pelicula;

import java.util.List;


public class FavoritesFragments extends Fragment {
    private static FavoritesPeliculasDatabase db;
    private static List<Pelicula> favoritePeliculas;
    private static RecyclerView rvFavoritos;
    private static PeliculaAdapter adapter;
    private static Context contexto;

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
        rvFavoritos = getView().findViewById(R.id.rv_favoritos);
        contexto = getActivity();
        getFavoritePeliculas();
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
            rvFavoritos.setLayoutManager((new LinearLayoutManager(contexto)));
            adapter = new PeliculaAdapter();
            adapter.setData(favoritePeliculas);
            rvFavoritos.setAdapter(adapter);
        }
    }
}
