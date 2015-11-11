/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.fabihur.android.mymovieapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable{

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("overview")
    private String overview;

    @SerializedName("vote_average")
    private String userRating;

    @SerializedName("release_date")
    private String releaseDate;

    public Movie(){

    }
    protected Movie(Parcel in){
        originalTitle = in.readString();
        poster = in.readString();
        overview = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalTitle);
        dest.writeString(poster);
        dest.writeString(overview);
        dest.writeString(userRating);
        dest.writeString(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
