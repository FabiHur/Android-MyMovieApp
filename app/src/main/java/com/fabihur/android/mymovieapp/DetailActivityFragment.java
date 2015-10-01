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


public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        View detailView = inflater.inflate(R.layout.fragment_detail, container, false);

        if(intent != null){

            String userRating = intent.getStringExtra(MovieUtils.USER_RATING);
            String releaseDate = (intent.getStringExtra(MovieUtils.RELEASE_DATE)).substring(0,4);

            ((TextView)detailView.findViewById(R.id.movie_title_text_view))
                    .setText(intent.getStringExtra(MovieUtils.ORIGINAL_TITLE));
            ((TextView)detailView.findViewById(R.id.release_date_text_view))
                    .setText(releaseDate);
            ((TextView)detailView.findViewById(R.id.user_rating_text_view))
                    .setText(userRating + getString(R.string.rating_max));
            ((TextView)detailView.findViewById(R.id.movie_overview_text_view))
                    .setText(intent.getStringExtra(MovieUtils.OVERVIEW));

            String poster = intent.getStringExtra(MovieUtils.POSTER_PATH);
            String path = getString(R.string.poster_base_path);

            ImageView posterImageView =
                    (ImageView)detailView.findViewById(R.id.movie_poster_image_view);
            Picasso.with(getActivity()).load(path+poster).into(posterImageView);
        }
        return detailView;
    }
}