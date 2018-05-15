package com.example.emily.moviesapp;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

final class NetworkUtils {

    private static final String MOVIE_DB_URL =
        "http://api.themoviedb.org/3/movie";

    //TODO: Enter themvoviedb.org API key in this string
    private static final String MY_API =
            "";

    private static final String API_PARAMETER = "api_key";
    private static final String LANGUAGE_PARAMETER = "language";
    private static final String LANGUAGE = "en-US";
    private static final String PAGES_PARAMETER = "page";
    private static final String PAGES = "1";

    public static URL buildUrl(String popularOrTopRated){

        Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(popularOrTopRated)
                .appendQueryParameter(API_PARAMETER, MY_API)
                .appendQueryParameter(LANGUAGE_PARAMETER, LANGUAGE)
                .appendQueryParameter(PAGES_PARAMETER, PAGES)
                .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponsefromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
