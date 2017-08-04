package com.madalinaloghin.movieapp.api.response;

import com.google.gson.annotations.SerializedName;
import com.madalinaloghin.util.object.TvSeries;

import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class ResponseListTvSeries {

    @SerializedName("results")
    private ArrayList<TvSeries> resultsList;

    public ArrayList<TvSeries> getResultsList() {
        return resultsList;
    }
}
