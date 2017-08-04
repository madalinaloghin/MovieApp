package com.madalinaloghin.util.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class TvSeries implements Serializable {


    @SerializedName("backdrop_path")
    private String imageUrl;

    @SerializedName("created_by")
    private ArrayList<Person> createdBy;

    @SerializedName("epidode_run_time")
    private ArrayList<Integer> episodeRunTime;

    @SerializedName("first_air_date")
    private String first_air_date;

    @SerializedName("genres")
    private ArrayList<Genre> genres;

    @SerializedName("id")
    private int id;

    @SerializedName("in_production")
    private boolean inProduction;

    @SerializedName("number_of_episodes")
    private int nrEpisodes;

    @SerializedName("number_of_seasons")
    private int nrSeasons;

    @SerializedName("poster_path")
    private String posterUrl;

    @SerializedName("name")
    private String title;

    @SerializedName("overview")
    private String description;

    @SerializedName("popularity")
    private float popularity;

    @SerializedName("status")
    private String status;

    @SerializedName("vote_average")
    private float voteAverage;

    @SerializedName("vote_count")
    private int voteCount;


    public boolean isFavorite;


    public String getImageUrl() {
        String url = "https://image.tmdb.org/t/p/w500" + imageUrl;
        return url;
    }

    public ArrayList<Person> getCreatedBy() {
        return createdBy;
    }

    public ArrayList<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public int getId() {
        return id;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public int getNrEpisodes() {
        return nrEpisodes;
    }

    public int getNrSeasons() {
        return nrSeasons;
    }

    public String getPosterUrl() {
        String url = "https://image.tmdb.org/t/p/w500" + posterUrl;
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getStatus() {
        return status;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }
}
