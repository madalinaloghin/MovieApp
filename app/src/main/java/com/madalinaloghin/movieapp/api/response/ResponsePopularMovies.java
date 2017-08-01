package com.madalinaloghin.movieapp.api.response;

import com.google.gson.annotations.SerializedName;
import com.madalinaloghin.util.object.Movie;

import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class ResponsePopularMovies {

    @SerializedName("results")
    private ArrayList<Movie> resultsList;

    public ArrayList<Movie> getResultsList() {
        return resultsList;
    }
}
