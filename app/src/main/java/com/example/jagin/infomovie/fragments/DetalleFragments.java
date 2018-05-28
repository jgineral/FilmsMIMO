package com.example.jagin.infomovie.fragments;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jagin.infomovie.BuildConfig;
import com.example.jagin.infomovie.R;
import com.example.jagin.infomovie.asyncTask.DeleteFavoriteTask;
import com.example.jagin.infomovie.asyncTask.InsertFavoriteTask;
import com.example.jagin.infomovie.db.FavoritesPeliculasDatabase;
import com.example.jagin.infomovie.model.Pelicula;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DetalleFragments extends Fragment {

    private Pelicula pelicula;
    private String media_votos;
    private String imagen;
    private boolean favorite;

    private static FavoritesPeliculasDatabase db;
    private static List<Pelicula> favoritePeliculas;

    public static DetalleFragments newInstance(){
        return new DetalleFragments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle_fragments, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pelicula = getActivity().getIntent().getParcelableExtra("pelicula");
        if(pelicula.isFavorite()){
            favorite = true;
        }
        else{
            favorite = false;
        }

        init(pelicula);
    }


    @Override
    public void onDestroy() {
        //Add o delete en onDestroy por si el usuario clickea redundantemente en el icono de favoritos.
        if(favorite){
            addToFavorites(pelicula);
        }
        else{
            deleteToFavorites(pelicula);
            Log.i("Deleted finalmente",pelicula.getTitle());
        }

        super.onDestroy();
    }



    private void addToFavorites(Pelicula pelicula){
        db = Room.databaseBuilder(getActivity(), FavoritesPeliculasDatabase.class, BuildConfig.DB_NAME).build();
        InsertFavoriteTask insertFavoriteTask = new InsertFavoriteTask(db);
        pelicula.setFavorite(true); //introducimos la pelicula como favorita (true)
        insertFavoriteTask.execute(pelicula);
    }

    private void deleteToFavorites(Pelicula pelicula){
        db = Room.databaseBuilder(getActivity(), FavoritesPeliculasDatabase.class, BuildConfig.DB_NAME).build();
        DeleteFavoriteTask deleteFavoriteTask = new DeleteFavoriteTask(db);
        deleteFavoriteTask.execute(pelicula);
    }


    private void init(Pelicula pelicula)
    {

        TextView tvTitle = getView().findViewById(R.id.tvTitle);
        ImageView ivBackdrop = getView().findViewById(R.id.ivBackdrop);
        TextView tvAverage = getView().findViewById(R.id.tvAverage);
        TextView tvDate = getView().findViewById(R.id.tvdate);
        TextView tvAdult = getView().findViewById(R.id.tvAdult);
        TextView tvOverview = getView().findViewById(R.id.tvOverview);
        final ImageView ivFavorite = getView().findViewById(R.id.ivFavorite);

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favorite){
                    favorite = false;
                    ivFavorite.setImageResource(R.drawable.star_off);
                    Snackbar.make(getView(),"Film deleted to favorites",Snackbar.LENGTH_LONG).show();

                }
                else{
                    favorite = true;
                    ivFavorite.setImageResource(R.drawable.star_on);
                    Snackbar.make(getView(),"Film added to favorites",Snackbar.LENGTH_LONG).show();
                }
            }
        });


        tvTitle.setText(pelicula.getTitle());
        imagen = BuildConfig.URL_IMG + pelicula.getBackdrop_path();

        Picasso.with(getActivity()).load(imagen).into(ivBackdrop);
        media_votos = Double.toString(pelicula.getVote_average());
        tvAverage.setText(media_votos);
        tvDate.setText(pelicula.getRelease_date());
        if(pelicula.isFavorite()){
            ivFavorite.setImageResource(R.drawable.star_on);
        }
        else{
            ivFavorite.setImageResource(R.drawable.star_off);
        }
        if(pelicula.isAdult()){
            tvAdult.setText(R.string.adulto_true);
        }else{
            tvAdult.setText(R.string.all_public);
        }
        tvOverview.setText(pelicula.getOverview());

    }
}
