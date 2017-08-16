package com.madalinaloghin.movieapp.api.response;

import com.google.gson.annotations.SerializedName;
import com.madalinaloghin.util.object.TvSeries;

import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/4/2017.
 */

public class ResponseListFavoriteTvSeries {

    @SerializedName("results")
    private ArrayList<TvSeries> resultsList;

    public ArrayList<TvSeries> getResultsList() {
        setFavoritesAtrib();
        return resultsList;
    }

    void setFavoritesAtrib() {
        for (TvSeries tv : resultsList) {
            tv.setFavorite(true);
        }
    }
}
