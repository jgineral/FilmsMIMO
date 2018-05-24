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
    public int id;

    @SerializedName("vote_average")
    public double vote_average;

    @SerializedName("title")
    public String title;
    @SerializedName("poster_path")
    public String poster_path;

    @SerializedName("original_language")
    public String original_language;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    public String release_date;

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
                '}';
    }

    public Pelicula(int id, double vote_average, String title, String poster_path, String original_language, boolean adult, String overview, String release_date) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
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
    }

    public static final Creator<Pelicula> CREATOR = new Creator<Pelicula>() {
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
