package com.example.jagin.infomovie.fragments;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
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
        rvFavoritos.setLayoutManager((new LinearLayoutManager(getActivity())));
        rvFavoritos.setItemAnimator(new DefaultItemAnimator());
        adapter = new PeliculaAdapter();

        getFavoritePeliculas();

    }


    private void deleteFavoritePelicula(int index){
        db = Room.databaseBuilder(getActivity(), FavoritesPeliculasDatabase.class, BuildConfig.DB_NAME).build();
        DeleteFavoriteTask deleteFavoriteTask = new DeleteFavoriteTask(index);
        deleteFavoriteTask.execute();
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
            adapter.setData(favoritePeliculas);
            rvFavoritos.setAdapter(adapter);
        }
    }

    private class DeleteFavoriteTask extends AsyncTask<Pelicula,Void,Void>{

        int positionRemoved;

        public DeleteFavoriteTask(int positionRemoved){
            this.positionRemoved = positionRemoved;
        }

        @Override
        protected Void doInBackground(Pelicula... peliculas) {
            db.favoritesPeliculasDao().delete(peliculas[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyItemRemoved(positionRemoved);
            //Volvemos a pintar lista Favoritos.
            GetFavoritesTask getFavoritesTask = new GetFavoritesTask();
            getFavoritesTask.execute();
            Snackbar.make(getView(),"Film deleted to favorites",Snackbar.LENGTH_LONG).show();
        }
    }

}
