package com.ulsa.newsapp.Model;

import java.util.List;

public class IconSourceModel {
    private String url;
    private List<Icons> icons;

    public IconSourceModel(String url) {
        this.url = url;

    }

    public String getUrl() {
        return url;
    }

    public List<Icons> getIcons() {
        return icons;
    }
}
