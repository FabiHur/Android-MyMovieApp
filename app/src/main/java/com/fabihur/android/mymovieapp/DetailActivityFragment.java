/*
 * Copyright (C) 2013 The Android Open Source Project
 */
package com.fabihur.android.mymovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabihur.android.mymovieapp.utils.MovieUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DetailActivityFragment extends Fragment {

    @Bind(R.id.movie_title_text_view) TextView movieTitleTextView;
    @Bind(R.id.release_date_text_view) TextView releaseDateTextView;
    @Bind(R.id.user_rating_text_view) TextView usrRatingTextView;
    @Bind(R.id.movie_overview_text_view) TextView overviewTextView;
    @Bind(R.id.movie_poster_image_view) ImageView posterImageView;


    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        View detailView = inflater.inflate(R.layout.fragment_detail, container, false);

        if(intent != null){
            ButterKnife.bind(this, detailView);

            Movie selectedMovie = intent.getExtras().getParcelable(MovieUtils.MOVIE);

            movieTitleTextView.setText(selectedMovie.getOriginalTitle());
            releaseDateTextView.setText(selectedMovie.getReleaseDate().substring(0, 4));
            usrRatingTextView.setText(selectedMovie.getUserRating() + getString(R.string.rating_max));
            overviewTextView.setText(selectedMovie.getOverview());

            String path = getString(R.string.poster_base_path);

            Picasso.with(getActivity())
                    .load(path + selectedMovie.getPoster())
                    .into(posterImageView);
        }
        return detailView;
    }
}