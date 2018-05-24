package com.example.jagin.infomovie.fragments;
import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.jagin.infomovie.BuildConfig;
import com.example.jagin.infomovie.R;

import com.example.jagin.infomovie.adapter.PeliculaAdapter;
import com.example.jagin.infomovie.db.FavoritesPeliculasDatabase;
import com.example.jagin.infomovie.model.Pelicula;
import com.example.jagin.infomovie.model.Results;
import com.example.jagin.infomovie.network.GsonRequest;
import com.example.jagin.infomovie.network.RequestManager;

import java.util.List;


public class PeliculasFragments extends Fragment {

    private static FavoritesPeliculasDatabase db;
    public static List<Pelicula> favoritePeliculas;
    public static List<Pelicula> peliculasList;

    private RecyclerView rvPeliculas;
    private PeliculaAdapter adapter;

    public static PeliculasFragments newInstance(){
        return new PeliculasFragments();

    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_peliculas_fragments, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getView() != null){
            rvPeliculas = getView().findViewById(R.id.rv_Peliculas);
            getPeliculas();

        }

    }

    private void getPeliculas(){
        GsonRequest gsonRequest = new GsonRequest<>(BuildConfig.URL, Results.class, null, new Response.Listener<Results>() {

            @Override
            public void onResponse(Results peliculas) {
                if(peliculas != null){
                    peliculasList = peliculas.getPeliculas();
                }
                //texto.setText(String.format("%s\n\n%s", getString(R.string.response_title), peliculas.toString()));
                rvPeliculas.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new PeliculaAdapter();
                adapter.setData(peliculasList);
                rvPeliculas.setAdapter(adapter);


                //addToFavorites(6);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        RequestManager.getInstance().addRequest(getActivity(), gsonRequest);
    }

    private void addToFavorites(int index){
        Pelicula pelicula = peliculasList.get(index);
        db = Room.databaseBuilder(getActivity(), FavoritesPeliculasDatabase.class, BuildConfig.DB_NAME).build();
        InsertFavoriteTask insertFavoriteTask = new InsertFavoriteTask();
        insertFavoriteTask.execute(pelicula);
    }

    private class InsertFavoriteTask extends AsyncTask<Pelicula, Void, Void> {

        @Override
        protected Void doInBackground(Pelicula... peliculas) {
            db.favoritesPeliculasDao().insert(peliculas[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Snackbar.make(getView(),"Film added to favorites",Snackbar.LENGTH_LONG).show();
        }
    }
}
