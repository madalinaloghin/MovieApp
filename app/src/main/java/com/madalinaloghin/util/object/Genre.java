package com.madalinaloghin.util.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class Genre {


    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }
}
