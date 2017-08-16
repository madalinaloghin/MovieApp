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


    private boolean isFavorite;


    public String getStatus() {
        return status;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    private Rated rated;

    public Rated getRated() {
        return rated;
    }

    public void setRated(Rated rated) {
        this.rated = rated;
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


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setKeywords(KeywordHolder keywords) {
        this.keywords = keywords;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
