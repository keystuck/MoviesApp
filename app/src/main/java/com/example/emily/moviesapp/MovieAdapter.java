package com.example.emily.moviesapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

class MovieAdapter extends ArrayAdapter<Movie> {


    public MovieAdapter(Activity context, List<Movie> movieList){
        super(context, 0, movieList);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, parent, false);

        }

        ImageView posterView = convertView.findViewById(R.id.iv_poster);
        if (movie.getPosterPath() == null || movie.getPosterPath().isEmpty()){
            posterView.setImageResource(R.drawable.no_image);
        }
        else {
            Picasso.with(getContext())
                    .load(movie.getPosterPath())
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(posterView);
        }
        return convertView;
    }

}