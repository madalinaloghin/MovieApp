package com.madalinaloghin.movieapp.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/8/2017.
 */

public class ResponseMultiSearch {

    @SerializedName("results")
    private ArrayList<Object> results;


    public ArrayList<Object> getResults() {
        return results;
    }
}
