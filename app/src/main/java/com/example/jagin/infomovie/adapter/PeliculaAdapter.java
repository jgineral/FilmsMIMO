package com.example.jagin.infomovie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jagin.infomovie.BuildConfig;
import com.example.jagin.infomovie.R;
import com.example.jagin.infomovie.model.Pelicula;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.Holder>{
    private List<Pelicula> peliculaList;
    private String media_votos;
    private String imagen;


    public void setData(List<Pelicula> peliculas){
        this.peliculaList = peliculas;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelicula_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final Pelicula pelicula = peliculaList.get(position);
        Log.i("peliculas3", peliculaList.get(position).toString());
        holder.tvTitle.setText(pelicula.getTitle());
        holder.tvFecha.setText(pelicula.getRelease_date());
        holder.tvIdioma.setText(pelicula.getOriginal_language());
        holder.tvOverview.setText(pelicula.getOverview());
        media_votos = Double.toString(pelicula.getVote_average());
        holder.tvVote_average.setText(media_votos);
        if(pelicula.isAdult()){
            holder.tvAdulto.setText(R.string.adulto_true);
        }else{
            holder.tvAdulto.setText("");
        }
        if(pelicula.isFavorite()){
            holder.tvFavorite.setText(R.string.favorito);
        }
        else {
            holder.tvFavorite.setText(R.string.no_favorite);
        }

        imagen = BuildConfig.URL_IMG + pelicula.getPoster_path();
        if(pelicula.getPoster_path() != null){
            Picasso.with(holder.ivPelicula.getContext()).load(imagen).into(holder.ivPelicula);

        }

    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if(peliculaList != null){
            itemCount = peliculaList.size();
        }
        return itemCount;
    }

    static class Holder extends RecyclerView.ViewHolder
    {
        TextView tvTitle;
        TextView tvVote_average;
        TextView tvFecha;
        TextView tvIdioma;
        TextView tvAdulto;
        TextView tvOverview;
        TextView tvFavorite;
        ImageView ivPelicula;

        public Holder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvVote_average = itemView.findViewById(R.id.tvVote_average);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvIdioma = itemView.findViewById(R.id.tvIdioma);
            tvAdulto = itemView.findViewById(R.id.tvAdulto);
            ivPelicula = itemView.findViewById(R.id.ivPelicula);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            tvFavorite = itemView.findViewById(R.id.tvFavorite);
        }
    }
}
