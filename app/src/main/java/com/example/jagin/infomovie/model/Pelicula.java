package com.example.jagin.infomovie.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity
public class Pelicula implements Parcelable {

    @SerializedName("id")
    @PrimaryKey
    private int id;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    private boolean favorite;

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", vote_average=" + vote_average +
                ", title='" + title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", original_language='" + original_language + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", favorite=" + favorite +
                '}';
    }

    public Pelicula(int id, double vote_average, String title, String poster_path, String original_language, boolean adult, String overview, String release_date, String backdrop_path, boolean favorite) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.backdrop_path = backdrop_path;
        this.favorite = favorite;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.vote_average);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.original_language);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.backdrop_path);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
    }

    protected Pelicula(Parcel in) {
        this.id = in.readInt();
        this.vote_average = in.readDouble();
        this.title = in.readString();
        this.poster_path = in.readString();
        this.original_language = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.release_date = in.readString();
        this.backdrop_path = in.readString();
        this.favorite = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Pelicula> CREATOR = new Parcelable.Creator<Pelicula>() {
        @Override
        public Pelicula createFromParcel(Parcel source) {
            return new Pelicula(source);
        }

        @Override
        public Pelicula[] newArray(int size) {
            return new Pelicula[size];
        }
    };
}
