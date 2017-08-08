package com.madalinaloghin.util.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by madalina.loghin on 8/7/2017.
 */

public class UserListDetail implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String description;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterUrl;

    @SerializedName("backdrop_path")
    private String imageUrl;

    @SerializedName("vote_average")
    private float voteAverage;

    @SerializedName("media_type")
    private String mediaType;

    public String getMediaType() {
        return mediaType;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterUrl() {
        String url = "https://image.tmdb.org/t/p/w500" + posterUrl;
        return url;
    }

    public String getImageUrl() {
        String url = "https://image.tmdb.org/t/p/w500" + imageUrl;
        return url;
    }

    public float getVoteAverage() {
        return voteAverage;
    }
}
