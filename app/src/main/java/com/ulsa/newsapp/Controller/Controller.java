package com.ulsa.newsapp.Controller;

import com.ulsa.newsapp.Interface.IconSourceGet;
import com.ulsa.newsapp.Interface.SourcesGet;
import com.ulsa.newsapp.Retrofit.SourceIconService;
import com.ulsa.newsapp.Retrofit.SourceService;

public class Controller {

    public static SourcesGet getSources() {
        return SourceService.getClient(Base.BASE_URL).create(SourcesGet.class);
    }

    public static IconSourceGet getIcon() {
        return SourceIconService.getClient().create(IconSourceGet.class);
    }

    public static String getApiUrl(String source, String apikey) {
        //    https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=API_KEY
        StringBuilder apiUrl = new StringBuilder(" https://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append(source).append("&apiKey=").append(apikey).toString();
    }
}
