package com.madalinaloghin.movieapp.api.response;

import com.google.gson.annotations.SerializedName;
import com.madalinaloghin.util.object.Images;

import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class ResponseImageList {

    @SerializedName("profiles")
    private ArrayList<Images> imageList;

    public ArrayList<Images> getImageList() {
        return imageList;
    }
}
