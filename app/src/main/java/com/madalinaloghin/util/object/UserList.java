package com.madalinaloghin.util.object;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madalina.loghin on 8/7/2017.
 */

public class UserList {

    @SerializedName("description")
    String description;

    @SerializedName("favorite_count")
    int favoriteCount;

    @SerializedName("id")
    int id;

    @SerializedName("item_count")
    int items;

    @SerializedName("name")
    String name;

    @Nullable
    @SerializedName("poster_path")
    String posterPath;

    @Nullable
    public String getPosterPath() {
        String url = "https://image.tmdb.org/t/p/w500" + posterPath;
        return url;
    }

    public String getDescription() {
        return description;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public int getId() {
        return id;
    }

    public int getItems() {
        return items;
    }

    public String getName() {
        return name;
    }
}
