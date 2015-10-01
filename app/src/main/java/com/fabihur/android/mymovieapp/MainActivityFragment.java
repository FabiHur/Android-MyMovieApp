/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.fabihur.android.mymovieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.fabihur.android.mymovieapp.utils.MovieUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment {

    private MovieAdapter movieAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        movieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gv = (GridView) rootView.findViewById(R.id.gridView_movie);
        gv.setAdapter(movieAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie movie = movieAdapter.getItem(position);

                    Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                    detailIntent.putExtra(MovieUtils.ORIGINAL_TITLE, movie.getOriginalTitle());
                    detailIntent.putExtra(MovieUtils.POSTER_PATH, movie.getPoster());
                    detailIntent.putExtra(MovieUtils.RELEASE_DATE, movie.getReleaseDate());
                    detailIntent.putExtra(MovieUtils.USER_RATING, movie.getUserRating());
                    detailIntent.putExtra(MovieUtils.OVERVIEW, movie.getOverview());
                    startActivity(detailIntent);
                }
            }
        );
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FetchMovieTask task = new FetchMovieTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String orderBy = prefs.getString(getString(R.string.order_by_key),
                getString(R.string.order_by_default_value));
        task.execute(orderBy);

    }
    public class FetchMovieTask extends AsyncTask<String, Void,List<Movie>> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected List<Movie> doInBackground(String... params) {
            HttpURLConnection urlConn = null;
            BufferedReader reader = null;
            String jsonMovie;

            try {
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.scheme(MovieUtils.SCHEME)
                        .authority(MovieUtils.AUTHORITY)
                        .appendPath(MovieUtils.PATH)
                        .appendPath(MovieUtils.DISCOVER_PATH)
                        .appendPath(MovieUtils.MOVIE_PATH)
                        .appendQueryParameter(MovieUtils.SORT_BY, params[0])
                        .appendQueryParameter(MovieUtils.API_KEY,getString(R.string.api_key));

                //Open the connection
                URL url = new URL(uriBuilder.build().toString()); //set the movie uri
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setRequestMethod(MovieUtils.REQUEST_METHOD);
                urlConn.connect();

                InputStream inputStream = urlConn.getInputStream();
                if(inputStream != null){
                    reader = new BufferedReader(new InputStreamReader((inputStream)));
                }
                StringBuffer sb = new StringBuffer();
                String line;
                while((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                }

                if(sb.length() > 0){
                    jsonMovie = sb.toString();
                    return getMovieDataFromJson(jsonMovie);
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error", e);
            } catch (JSONException e){
                Log.e(LOG_TAG, "Error in getMovieDataFromJSON", e);
            } finally {
                if(urlConn != null){
                    urlConn.disconnect();
                }
                if(reader != null){
                    try{
                        reader.close();
                    }catch (IOException e){
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies != null) {
                movieAdapter.clear();
                for (Movie movie: movies){
                    movieAdapter.add(movie);
                }
            }
        }


        private List<Movie> getMovieDataFromJson(String jsonMovieStr) throws JSONException {
            List<Movie> movies = new ArrayList<>();

            JSONObject jsonObj = new JSONObject((jsonMovieStr));
            JSONArray jsonArray = jsonObj.getJSONArray(MovieUtils.RESULTS);

            for(int i=0;i < jsonArray.length(); i++){
                Movie movie = new Movie();
                JSONObject jsonMovie = jsonArray.getJSONObject(i);

                movie.setOriginalTitle(jsonMovie.getString(MovieUtils.ORIGINAL_TITLE));
                movie.setPoster(jsonMovie.getString(MovieUtils.POSTER_PATH));
                movie.setOverview(jsonMovie.getString(MovieUtils.OVERVIEW));
                movie.setUserRating(jsonMovie.getString(MovieUtils.USER_RATING));
                movie.setReleaseDate(jsonMovie.getString(MovieUtils.RELEASE_DATE));

                movies.add(movie);
            }
            return movies;
        }
    }
}
