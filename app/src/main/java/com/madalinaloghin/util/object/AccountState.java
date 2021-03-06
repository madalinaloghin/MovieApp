package com.madalinaloghin.util.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madalina.loghin on 8/11/2017.
 */

public class AccountState {

    @SerializedName("id")
    private int id;

    @SerializedName("favorite")
    private boolean favorite;


    @SerializedName("rated")
    private Rated rated;


    @SerializedName("watchlist")
    private boolean watchlist;

    public boolean getFavorite() {
        return favorite;
    }

    public boolean getWatchlist() {
        return watchlist;
    }

    public int getId() {
        return id;
    }


    public Rated getRated() {
        return rated;
    }

}
