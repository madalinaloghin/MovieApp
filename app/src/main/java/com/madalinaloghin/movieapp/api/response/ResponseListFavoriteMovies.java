package com.madalinaloghin.movieapp.api.response;

import com.google.gson.annotations.SerializedName;
import com.madalinaloghin.util.object.Movie;

import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/7/2017.
 */

public class ResponseListFavoriteMovies {

    @SerializedName("results")
    private ArrayList<Movie> resultsList;

    public ArrayList<Movie> getResultsList() {
        setFavoritesAtrib();
        return resultsList;
    }

    void setFavoritesAtrib(){
        for (Movie movie: resultsList) {
            movie.setFavorite(true);
        }
    }
}
