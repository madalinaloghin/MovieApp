package com.madalinaloghin.util.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madalina.loghin on 8/11/2017.
 */

public class AccountState {

    @SerializedName("id")
    private Long id;


    @SerializedName("favorite")
    private Boolean favorite;


    @SerializedName("rated")
    private Rated rated;


    @SerializedName("watchlist")
    private Boolean watchlist;

    public Boolean getFavorite() {
        return favorite;
    }

    public Boolean getWatchlist() {
        return watchlist;
    }

    public Long getId() {
        return id;
    }

    public Rated getRated() {
        return rated;
    }
}
