package com.madalinaloghin.util.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madalina.loghin on 8/11/2017.
 */

public class Rated {

    @SerializedName("value")
    private float value;

    public float getValue() {
        return value;
    }

    public Rated(float value) {
        this.value = value;
    }


}
