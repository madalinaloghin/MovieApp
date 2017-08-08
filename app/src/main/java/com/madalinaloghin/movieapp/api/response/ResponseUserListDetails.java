package com.madalinaloghin.movieapp.api.response;

import com.google.gson.annotations.SerializedName;
import com.madalinaloghin.util.object.UserListDetail;

import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/7/2017.
 */

public class ResponseUserListDetails {


    @SerializedName("items")
    private ArrayList<UserListDetail> resultsList;

    public ArrayList<UserListDetail> getResultsList() {
        return resultsList;
    }


}
