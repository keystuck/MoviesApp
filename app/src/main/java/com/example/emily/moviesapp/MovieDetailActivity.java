package com.example.emily.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String MOVIE_EXTRA = "movie";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        ImageView bigPosterImageView = findViewById(R.id.iv_big_poster);
        TextView titleTextView = findViewById(R.id.tv_movie_title);
        TextView summaryTextView = findViewById(R.id.tv_movie_summary);
        TextView voteAvgTextView = findViewById(R.id.tv_movie_vote_average);
        TextView releaseDateTextView = findViewById(R.id.tv_movie_release_date);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(MOVIE_EXTRA)){
            finish();
            Toast.makeText(this, "No movie data", Toast.LENGTH_SHORT).show();
        }
        Movie movie = intent.getParcelableExtra(MOVIE_EXTRA);

        if (movie == null){
            String errorString = getResources().getString(R.string.error_detail);
            movie = new Movie(errorString, "", errorString, 0.0, errorString, 0.0);

        }



        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        bigPosterImageView.getLayoutParams().height = height/3;
        bigPosterImageView.getLayoutParams().width = width;

        if (!movie.getPosterPath().isEmpty()) {

            Picasso.with(this).load(movie.getPosterPath())
                    .resize(width, height / 3)
                    .centerCrop()
                    .into(bigPosterImageView);
        }
        else {
            bigPosterImageView.setImageResource(R.drawable.no_image);
        }
        titleTextView.setText(movie.getTitle());
        summaryTextView.setText(movie.getSynopsis());
        String rating = movie.getRating() + getResources().getString(R.string.out_of_stars);
        voteAvgTextView.setText(rating);
        releaseDateTextView.setText(movie.getReleaseDate());

    }
}
