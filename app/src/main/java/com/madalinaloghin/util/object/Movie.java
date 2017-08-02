package com.madalinaloghin.util.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class Movie implements Serializable {

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

    @SerializedName("vote_count")
    private int voteCount;


    @SerializedName("keywords")
    private KeywordHolder keywords;

    @SerializedName("budget")
    private float budget;

    @SerializedName("genres")
    private ArrayList<Genre> genres;

    @SerializedName("popularity")
    private float popularity;


    @SerializedName("status")
    private String status;


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

    public KeywordHolder getKeywords() {
        return keywords;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getImageUrl() {
        String url = "https://image.tmdb.org/t/p/w500" + imageUrl;
        return url;
    }


    public float getBudget() {
        return budget;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public float getPopularity() {
        return popularity;
    }
}
