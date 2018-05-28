package com.example.jagin.infomovie.fragments;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jagin.infomovie.BuildConfig;
import com.example.jagin.infomovie.R;
import com.example.jagin.infomovie.adapter.PeliculaAdapter;
import com.example.jagin.infomovie.asyncTask.CountTask;
import com.example.jagin.infomovie.asyncTask.GetFavoritesTask;
import com.example.jagin.infomovie.db.FavoritesPeliculasDatabase;
import com.example.jagin.infomovie.model.Pelicula;

import java.util.List;


public class FavoritesFragments extends Fragment {
    private static FavoritesPeliculasDatabase db;
    private static List<Pelicula> favoritePeliculas;
    private static RecyclerView rvFavoritos;
    private static PeliculaAdapter adapter;
    private static TextView tvNoFavorites;

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
        tvNoFavorites = getView().findViewById(R.id.tvInfoList);
        rvFavoritos.setLayoutManager((new LinearLayoutManager(getActivity())));
        rvFavoritos.setItemAnimator(new DefaultItemAnimator());
        adapter = new PeliculaAdapter();

        getFavoritePeliculas();
        countFavoritesItems();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(db!=null){
            db.close();
        }

    }

    private void countFavoritesItems(){
        db = Room.databaseBuilder(getActivity(), FavoritesPeliculasDatabase.class, BuildConfig.DB_NAME).build();
        CountTask countTask = new CountTask(db, tvNoFavorites);
        countTask.execute();
    }

    private void getFavoritePeliculas(){
        db = Room.databaseBuilder(getActivity(), FavoritesPeliculasDatabase.class, BuildConfig.DB_NAME).build();
        GetFavoritesTask getFavoritesTask = new GetFavoritesTask(db,favoritePeliculas,adapter,rvFavoritos);
        getFavoritesTask.execute();
    }

}
