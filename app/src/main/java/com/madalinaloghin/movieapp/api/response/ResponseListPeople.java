package com.madalinaloghin.movieapp.api.response;

import com.google.gson.annotations.SerializedName;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.Person;

import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class ResponseListPeople {

    @SerializedName("results")
    private ArrayList<Person> resultsList;

    public ArrayList<Person> getResultsList() {
        return resultsList;
    }
}
