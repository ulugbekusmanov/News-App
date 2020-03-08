package com.ulsa.newsapp.Retrofit;

import com.ulsa.newsapp.Controller.Base;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SourceIconService {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Base.SOURCE_BASE_IMG)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
