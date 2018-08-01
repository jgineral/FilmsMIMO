package com.mimo.jagin.infomovie.fragments;
import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.mimo.jagin.infomovie.BuildConfig;
import com.mimo.jagin.infomovie.R;
import com.mimo.jagin.infomovie.activities.PeliculaActivity;
import com.mimo.jagin.infomovie.adapter.PeliculaAdapter;
import com.mimo.jagin.infomovie.asyncTask.GetIdTask;
import com.mimo.jagin.infomovie.db.FavoritesPeliculasDatabase;
import com.mimo.jagin.infomovie.model.Pelicula;
import com.mimo.jagin.infomovie.model.Results;
import com.mimo.jagin.infomovie.network.GsonRequest;
import com.mimo.jagin.infomovie.network.RequestManager;

import java.util.List;


public class PeliculasFragments extends Fragment {

    private static FavoritesPeliculasDatabase db;
    public static List<Pelicula> peliculasList;

    private  RecyclerView rvPeliculas;
    private static PeliculaAdapter adapter;

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
            //Comparar los id. para ver si isFavorite

            rvPeliculas = getView().findViewById(R.id.rv_Peliculas);
            rvPeliculas.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new PeliculaAdapter();
            //Network
            getPeliculas();

        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(db != null){
            db.close();
        }

    }
    //Obtenemos las Peliculas. Network
    private void getPeliculas(){
        GsonRequest gsonRequest = new GsonRequest<>(BuildConfig.URL, Results.class, null, new Response.Listener<Results>() {

            @Override
            public void onResponse(Results peliculas) {
                if(peliculas != null){
                    peliculasList = peliculas.getPeliculas();
                    for (int i = 0; i < peliculasList.size(); i++) {
                        //Comprobamos si hay alguna favorita para cambiar el recycler de favoritos.
                        getFavoritePeliculaById(i);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestManager.getInstance().addRequest(getActivity(), gsonRequest);
    }

    private void getFavoritePeliculaById(int index){
        Pelicula pelicula = peliculasList.get(index);
        db = Room.databaseBuilder(getActivity(), FavoritesPeliculasDatabase.class, BuildConfig.DB_NAME).build();
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail(rvPeliculas.getChildAdapterPosition(v));
            }
        });
        GetIdTask getIdTask = new GetIdTask(db,peliculasList,adapter,rvPeliculas);
        getIdTask.execute(pelicula.getId());
    }

    private void detail(int position) {
        Pelicula pelicula;
        pelicula = peliculasList.get(position);
        Intent myIntent = new Intent();
        myIntent.setClass(getActivity(), PeliculaActivity.class);
        myIntent.putExtra("pelicula", pelicula);
        startActivity(myIntent);
    }


}
