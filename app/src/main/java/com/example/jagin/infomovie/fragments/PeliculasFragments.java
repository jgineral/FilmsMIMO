package com.example.jagin.infomovie.fragments;
import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.jagin.infomovie.BuildConfig;
import com.example.jagin.infomovie.R;

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
    private static TextView texto;

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
            texto = getView().findViewById(R.id.tv_fragmentPeliculas);
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
                texto.setText(String.format("%s\n\n%s", getString(R.string.response_title), peliculas.toString()));

                //addToFavorites(6);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                texto.setText(R.string.error);

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
