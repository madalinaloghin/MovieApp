package com.madalinaloghin.movieapp.api.response;

import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.TvSeries;

import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class ResponsePopularTvSeries {

    @SerializedName("results")
    private ArrayList<TvSeries> resultsList;

    public ArrayList<TvSeries> getResultsList() {
        return resultsList;
    }
}
