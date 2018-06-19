package com.fastmaxi.fastmaxisydney.Util;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service_Generator {
    //base url
    public static final String API_BASE_URL = "http://fastmaxi.com.au/";

    private static OkHttpClient.Builder httpclient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());


    public static <S> S Create_Service(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpclient.build()).build();

        return retrofit.create(serviceClass);
    }


}
