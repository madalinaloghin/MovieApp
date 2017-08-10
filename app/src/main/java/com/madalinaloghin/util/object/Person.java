package com.madalinaloghin.util.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class Person implements Serializable {
    @SerializedName("birthday")
    private String birthday;

    @SerializedName("deathday")
    private String deathday;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("also_knows_as")
    private ArrayList<String> alias;

    @SerializedName("gender")
    private int genderId;

    @SerializedName("biography")
    private String biography;

    @SerializedName("popularity")
    private float popularity;

    @SerializedName("profile_path")
    private String imagePath;

    @SerializedName("place_of_birth")
    private String placeOfBirth;



    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getAlias() {
        return alias;
    }

    public int getGenderId() {
        return genderId;
    }

    public String getBiography() {
        return biography;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getImagePath() {
        String url = "https://image.tmdb.org/t/p/w500" + imagePath;
        return url;
    }

    public int getId() {
        return id;
    }
}
