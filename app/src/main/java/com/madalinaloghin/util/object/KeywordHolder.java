package com.madalinaloghin.util.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class KeywordHolder {

    @SerializedName("keywords")
    private ArrayList<Keyword> keywordList;


    public ArrayList<Keyword> getKeywordList() {
        return keywordList;
    }
}
