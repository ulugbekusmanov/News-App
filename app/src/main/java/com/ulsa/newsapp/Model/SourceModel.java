package com.ulsa.newsapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SourceModel {
    @SerializedName("status")
    private String status;
    @SerializedName("sources")
    private List<Sources> sources;

    public SourceModel() {
    }

    public String getStatus() {
        return status;
    }

    public List<Sources> getSources() {
        return sources;
    }
}
