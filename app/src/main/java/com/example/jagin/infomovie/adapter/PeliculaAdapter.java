package com.example.jagin.infomovie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.Holder> implements View.OnClickListener{
    private List<Pelicula> peliculaList;
    private View.OnClickListener listener;



    public void setData(List<Pelicula> peliculas){
        this.peliculaList = peliculas;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelicula_item, parent, false);
        view.setOnClickListener(this);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final Pelicula pelicula = peliculaList.get(position);
        holder.tvTitle.setText(pelicula.getTitle());
        holder.tvFecha.setText(pelicula.getRelease_date());
        holder.tvIdioma.setText(pelicula.getOriginal_language());
        holder.tvOverview.setText(pelicula.getOverview());
        String media_votos = Double.toString(pelicula.getVote_average());
        holder.tvVote_average.setText(media_votos);
        if(pelicula.isAdult()){
            holder.tvAdulto.setText(R.string.adulto_true);
        }else{
            holder.tvAdulto.setText("");
        }
        if(pelicula.isFavorite()){
            holder.ivFavorite.setImageResource(R.drawable.ic_star);
        }
        else {
            holder.ivFavorite.setImageResource(R.drawable.ic_star_border);
        }

        String imagen = BuildConfig.URL_IMG + pelicula.getPoster_path();
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

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    static class Holder extends RecyclerView.ViewHolder
    {
        TextView tvTitle;
        TextView tvVote_average;
        TextView tvFecha;
        TextView tvIdioma;
        TextView tvAdulto;
        TextView tvOverview;
        ImageView ivFavorite;
        ImageView ivPelicula;

        private Holder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvVote_average = itemView.findViewById(R.id.tvVote_average);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvIdioma = itemView.findViewById(R.id.tvIdioma);
            tvAdulto = itemView.findViewById(R.id.tvAdulto);
            ivPelicula = itemView.findViewById(R.id.ivPelicula);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
        }
    }
}
