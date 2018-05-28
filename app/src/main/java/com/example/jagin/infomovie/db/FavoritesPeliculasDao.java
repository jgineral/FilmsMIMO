package com.example.jagin.infomovie.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jagin.infomovie.model.Pelicula;

import java.util.List;


@Dao
public interface FavoritesPeliculasDao {
    @Query("SELECT * FROM Pelicula")
    List<Pelicula> getAll();

    @Query("SELECT * FROM Pelicula WHERE id = :id")
    Pelicula findPelilculaWithId(int id);

    @Query("SELECT COUNT(*) FROM Pelicula")
    int countPelilculas();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pelicula pelicula);

    @Delete()
    void delete(Pelicula pelicula);
}
