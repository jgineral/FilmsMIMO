package com.example.jagin.infomovie.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.jagin.infomovie.model.Pelicula;


@Database(entities = {Pelicula.class}, version = 3, exportSchema = false)
public abstract class FavoritesPeliculasDatabase extends RoomDatabase{
    public abstract FavoritesPeliculasDao favoritesPeliculasDao();

}
