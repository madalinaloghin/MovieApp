package com.madalinaloghin.util.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madalina.loghin on 7/31/2017.
 */

public class User {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
}
