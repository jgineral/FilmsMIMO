package com.example.jagin.infomovie.fragments;
import android.app.Activity;
import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    public static List<Pelicula> peliculasList;

    private static RecyclerView rvPeliculas;
    private static PeliculaAdapter adapter;
    private static Activity activity;

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
                activity = getActivity();
                if(peliculas != null){
                    peliculasList = peliculas.getPeliculas();
                    for (int i = 0; i < peliculasList.size(); i++) {
                        getFavoritePeliculaById(i);
                    }
                }
                //addToFavorites(0);
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
        pelicula.setFavorite(true); //introducimos la pelicula como favorita (true).
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

    ///////////////////////

    private void getFavoritePeliculaById(int index){
        Pelicula pelicula = peliculasList.get(index);
        db = Room.databaseBuilder(getActivity(), FavoritesPeliculasDatabase.class, BuildConfig.DB_NAME).build();
        PeliculasFragments.GetIdTask getIdTask = new PeliculasFragments.GetIdTask();
        //Comprobamos si hay cambios en la base de datos, y si es así pintamos.
        getIdTask.execute(pelicula.getId());



    }

    private static class GetIdTask extends AsyncTask<Integer, Void, Pelicula> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Pelicula peliculaEncontrada;
        }

        @Override
        protected Pelicula doInBackground(Integer... integers) {
            return db.favoritesPeliculasDao().findPelilculaWithId(integers[0]);

        }

        @Override
        protected void onPostExecute(Pelicula pelicula) {
            super.onPostExecute(pelicula);
            if(pelicula != null) {
                Log.i("peliculaDao",pelicula.toString());
                for (int i = 0; i < peliculasList.size() ; i++) {
                    if(peliculasList.get(i).getId() == pelicula.getId()){
                        peliculasList.get(i).setFavorite(true);
                    }
                }
            }
            /*Como existe la posibilidad de que encuentre un favoritos en la base de datos, debemos de pintar que esta favorito.
            por tanto pintar aquí. */

            rvPeliculas.setLayoutManager(new LinearLayoutManager(activity));
            adapter = new PeliculaAdapter();
            Log.i("peliculas 2", peliculasList.get(1).toString());
            adapter.setData(peliculasList);
            rvPeliculas.setAdapter(adapter);
        }

    }
}
