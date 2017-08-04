package com.madalinaloghin.util.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/3/2017.
 */

public class UserList {

    @SerializedName("results")
    private ArrayList<UserList> resultsList;

    public ArrayList<UserList> getResultsList() {
        return resultsList;
    }

}
