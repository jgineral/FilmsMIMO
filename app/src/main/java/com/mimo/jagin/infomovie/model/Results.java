package com.mimo.jagin.infomovie.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Results {
    @SerializedName("results")
    private List<Pelicula> peliculas = new ArrayList<>();

    public List<Pelicula> getPeliculas(){
        return peliculas;
    }

    @Override
    public String toString() {
        StringBuilder description = new StringBuilder();
        if(peliculas!=null && peliculas.size()>0){
            for (Pelicula pelicula : peliculas) {
                description.append(pelicula.getTitle()).append("\n");
            }
        }
        return description.toString();
    }
}
