/* Emily Stuckey, 2018
 * Some code patterned after the Udacity Sunshine app
 * Connectivity-checking code is adapted from
 * https://stackoverflow.com/a/27312494
 *
 */


package com.example.emily.moviesapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;
    private GridView mGridView;
    private TextView mErrorMessageView;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String POPULAR =
            "popular";

    private static final String TOP_RATED =
            "top_rated";

    private static final String NO_INTERNET = "No Internet";
    private static final String OTHER_ERROR = "Some error";
    private static final String NO_PROBLEM = "OK";

    private String CURRENT_SORT = POPULAR;

    private String moviesJSON = "";

    //placeholder array
    private final ArrayList<Movie> movies = new ArrayList<>(Arrays.asList(
            new Movie("Default movie", "", "", 0.0, "", 300.2)
    ));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = findViewById(R.id.grid_view);
        mErrorMessageView = findViewById(R.id.tv_error_message_display);

        mGridView.setAdapter(movieAdapter);
        new GetMovieListTask().execute(POPULAR);

        //"movies" is a placeholder until the AsyncTask finishes
        movieAdapter = new MovieAdapter(this, movies);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                launchDetailActivity(i);
            }
        });
    }

    private void launchDetailActivity(int position){
        Movie clickedMovie = movieAdapter.getItem(position);
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_EXTRA, clickedMovie);
        startActivity(intent);
    }

    private void showErrorMessage(){
        mGridView.setVisibility(View.INVISIBLE);
        mErrorMessageView.setVisibility(View.VISIBLE);
    }

    public class GetMovieListTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            if (strings.length == 0){
                return null;
            }
            URL movieListUrl = NetworkUtils.buildUrl(strings[0]);

            try {
                // Code in this try block is adapted from Levit:
                // https://stackoverflow.com/a/27312494

                int timeout = 1500;
                Socket socket = new Socket();
                //use Google's DNS address to check connectivity
                SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);

                socket.connect(socketAddress, timeout);
                socket.close();
            } catch (IOException e) {
                return NO_INTERNET;

            }
            try {
                moviesJSON = NetworkUtils
                        .getResponsefromHttpUrl(movieListUrl);
                return NO_PROBLEM;
            } catch (Exception e){
                e.printStackTrace();
                return OTHER_ERROR;
            }
        }

        @Override
        protected void onPostExecute(String s) {

            if (s.equals(NO_PROBLEM)) {

                try {

                    ArrayList<Movie> moviesFromJSON = JSONUtils.getMoviesFromJSON(moviesJSON);
                    if (moviesFromJSON == null){
                        showErrorMessage();
                    } else {
                        movieAdapter.clear();
                        movieAdapter.addAll(moviesFromJSON);

                        mErrorMessageView.setVisibility(View.INVISIBLE);
                        mGridView.setVisibility(View.VISIBLE);

                        mGridView.setAdapter(movieAdapter);
                    }
                } catch (JSONException e) {
                    Log.v(LOG_TAG, "JSONException");
                    showErrorMessage();
                }
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_sortPopular:
                if (CURRENT_SORT.equals(TOP_RATED)) {
                    new GetMovieListTask().execute(POPULAR);
                    CURRENT_SORT = POPULAR;
                } else {
                    Toast.makeText(this, "Already sorted by popularity", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.action_sortRating:
                if (CURRENT_SORT.equals(POPULAR)) {
                    new GetMovieListTask().execute(TOP_RATED);
                    CURRENT_SORT = TOP_RATED;
                } else {
                    Toast.makeText(this, "Already sorted by user rating", Toast.LENGTH_SHORT).show();
                }
                return true;

            default:

                Toast.makeText(this, "Option not available", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }


}
