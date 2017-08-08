package com.madalinaloghin.movieapp.api.response;

import com.google.gson.annotations.SerializedName;
import com.madalinaloghin.util.object.UserList;

import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/3/2017.
 */

public class ResponseUserLists {

    @SerializedName("results")
    private ArrayList<UserList> resultsList;

    public ArrayList<UserList> getResultsList() {
        return resultsList;
    }


}
