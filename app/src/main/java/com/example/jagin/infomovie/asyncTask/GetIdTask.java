package com.example.jagin.infomovie.asyncTask;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jagin.infomovie.adapter.PeliculaAdapter;
import com.example.jagin.infomovie.db.FavoritesPeliculasDatabase;
import com.example.jagin.infomovie.model.Pelicula;

import java.util.List;

public class GetIdTask extends AsyncTask<Integer, Void, Pelicula> {
    private FavoritesPeliculasDatabase db;
    private List<Pelicula> peliculaList;
    private PeliculaAdapter adapter;
    private RecyclerView rvPeliculas;

    public GetIdTask(FavoritesPeliculasDatabase db, List<Pelicula> peliculaList, PeliculaAdapter adapter, RecyclerView rvPeliculas) {
        this.db = db;
        this.peliculaList = peliculaList;
        this.adapter = adapter;
        this.rvPeliculas = rvPeliculas;
    }

    @Override
    protected Pelicula doInBackground(Integer... integers) {
        return db.favoritesPeliculasDao().findPelilculaWithId(integers[0]);

    }

    @Override
    protected void onPostExecute(Pelicula pelicula) {
        super.onPostExecute(pelicula);

        if(pelicula != null) {
            for (int i = 0; i < peliculaList.size() ; i++) {
                if(peliculaList.get(i).getId() == pelicula.getId()){
                    peliculaList.get(i).setFavorite(true);
                }
            }
        }
       /*Como existe la posibilidad de que encuentre un favoritos en la base de datos, debemos de pintar que esta favorito.
        por tanto pintar aquí. */
        adapter.setData(peliculaList);
        rvPeliculas.setAdapter(adapter);
        db.close();
    }

}