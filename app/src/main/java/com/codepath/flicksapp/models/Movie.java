package com.codepath.flicksapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Saranu on 3/7/17.
 */

public class Movie implements Serializable {
    public String posterPath;
    public String originalTitle;
    public String overview;
    public String backdropPath;
    public String movieId;
    public float popularRating;
    public String releaseDate;


    public Movie(JSONObject jsonObject) throws JSONException {
            this.posterPath = jsonObject.getString("poster_path");
            this.originalTitle = jsonObject.getString("original_title");
            this.overview = jsonObject.getString("overview");
            this.backdropPath = jsonObject.getString("backdrop_path");
            this.movieId = jsonObject.getString("id");
            this.popularRating =  Float.parseFloat(jsonObject.getString("vote_average"));
            this.releaseDate = jsonObject.getString("release_date");

     }

    public static ArrayList<Movie> fromJSONArray(JSONArray jArray) throws JSONException{
        ArrayList<Movie> results = new ArrayList<>();
         for (int j=0; j<jArray.length();j++)
        {
            if(jArray.getJSONObject(j) != null && jArray.getJSONObject(j).getString("id") != null )
                results.add(new Movie(jArray.getJSONObject(j)));
        }

        return results;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s" ,posterPath);
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s" ,backdropPath);
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public float getPopularRating() {
        return popularRating;
    }

    public void setPopularRating(float rating) {
        this.popularRating = rating;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


}
