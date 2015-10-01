/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.fabihur.android.mymovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        String poster = "";

        if(movie.getPoster() != null){
            poster = movie.getPoster();
        }
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.grid_item_movie, parent, false);
        }

        ImageView posterImageView = (ImageView)convertView.findViewById(R.id.imageView_movie);
        Picasso.with(getContext())
                .load(getContext().getString(R.string.poster_base_path) + poster)
                .into(posterImageView);

        return convertView;
    }
}
