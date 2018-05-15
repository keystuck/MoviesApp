package com.example.emily.moviesapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

final class JSONUtils {
    private static final String LOG_TAG = JSONUtils.class.getSimpleName();

    private static final String NUM_RESULTS = "total_results";
    private static final String RESULTS = "results";

    private static final String TITLE = "title";
    private static final String VOTE_AVG = "vote_average";
    private static final String POSTER_PATH = "poster_path";
    private static final String RELEASE_DATE = "release_date";
    private static final String SUMMARY = "overview";
    private static final String POP = "popularity";


    public static ArrayList<Movie> getMoviesFromJSON(String JSONInputString) throws JSONException{
        if (JSONInputString.isEmpty()){
            return null;
        }
        JSONObject inputAsJSON = new JSONObject(JSONInputString);
        int howManyMovies = inputAsJSON.getInt(NUM_RESULTS);

        if (howManyMovies == 0){
            return null;
        }


        JSONArray inputArray = inputAsJSON.getJSONArray(RESULTS);
        ArrayList<Movie> moviesList= new ArrayList<>();

        for (int i = 0; i < inputArray.length(); i++){
            String title;
            String posterPath;
            String synopsis;
            double rating;
            String releaseDate;
            double popularity;

            JSONObject currentMovieJSON = (JSONObject)inputArray.get(i);

            title = currentMovieJSON.getString(TITLE);
            posterPath = currentMovieJSON.getString(POSTER_PATH);
            synopsis = currentMovieJSON.getString(SUMMARY);
            rating = currentMovieJSON.getDouble(VOTE_AVG);
            releaseDate = currentMovieJSON.getString(RELEASE_DATE);
            popularity = currentMovieJSON.getDouble(POP);

            moviesList.add(new Movie(title, posterPath, synopsis, rating, releaseDate, popularity));
            Log.v(LOG_TAG, "added movie #" + i);
        }
        return moviesList;
    }

}
