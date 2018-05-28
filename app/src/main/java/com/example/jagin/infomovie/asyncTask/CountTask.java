package com.example.jagin.infomovie.asyncTask;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.example.jagin.infomovie.db.FavoritesPeliculasDatabase;

public class CountTask extends AsyncTask<Void, Void, Integer> {
    private FavoritesPeliculasDatabase db;
    private TextView tv;

    public CountTask(FavoritesPeliculasDatabase db, TextView tv) {
        this.db = db;
        this.tv = tv;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return db.favoritesPeliculasDao().countPelilculas();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer == 0){
            tv.setVisibility(View.VISIBLE);
        }
        db.close();
    }
}
