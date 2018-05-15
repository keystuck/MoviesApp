package com.example.emily.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private final String title;
    private final String posterPath;
    private final String synopsis;
    private final double rating;
    private final String releaseDate;
    private final double popularity;


    private Movie(Parcel in){
        title = in.readString();
        posterPath = in.readString();
        synopsis = in.readString();
        rating = in.readDouble();
        releaseDate = in.readString();
        popularity = in.readDouble();
    }

    public Movie(String name, String image, String summary, double vote, String relDate, double pop){
        title = name;
        posterPath = "http://image.tmdb.org/t/p/w185//" + image;
        synopsis = summary;
        rating = vote;
        releaseDate = relDate;
        popularity = pop;

    }

    public static final Parcelable.Creator<Movie> CREATOR =
            new Parcelable.Creator<Movie>(){
        public Movie createFromParcel(Parcel in){
            return new Movie(in);
        }
        public Movie[] newArray(int size){
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(synopsis);
        parcel.writeDouble(rating);
        parcel.writeString(releaseDate);
        parcel.writeDouble(popularity);
    }


}
