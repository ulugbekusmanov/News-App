package com.ulsa.newsapp.Interface;

import com.ulsa.newsapp.Model.News;
import com.ulsa.newsapp.Model.SourceModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface SourcesGet {

    @GET("v2/sources?apiKey=0674e7f98e8e4690825e8527392a8321")
    Call<SourceModel> getSources();


    @GET
    Call<News> getNews(@Url String url);

}
