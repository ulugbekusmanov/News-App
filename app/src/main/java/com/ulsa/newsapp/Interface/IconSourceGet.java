package com.ulsa.newsapp.Interface;

import com.ulsa.newsapp.Model.IconSourceModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IconSourceGet {
    @GET
    Call <IconSourceModel> getIconUrl(@Url String url);


}
