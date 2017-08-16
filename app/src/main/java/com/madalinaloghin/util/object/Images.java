package com.madalinaloghin.util.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madalina.loghin on 8/11/2017.
 */

public class Images {


    @SerializedName("width")
    private Long width;

    @SerializedName("height")
    private Long height;

    @SerializedName("vote_count")
    private Long voteCount;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("file_path")
    private String filePath;

    @SerializedName("aspect_ratio")
    private Double aspectRatio;

    public Double getAspectRatio() {
        return aspectRatio;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Long getHeight() {
        return height;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public Long getWidth() {
        return width;
    }


    public String getFilePath() {

        String url = "https://image.tmdb.org/t/p/w500" + filePath;
        return url;
    }
}
