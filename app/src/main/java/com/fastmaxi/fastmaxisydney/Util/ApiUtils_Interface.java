package com.fastmaxi.fastmaxisydney.Util;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiUtils_Interface {
//    @FormUrlEncoded
//    @Headers("Content-Type: application/json")
//    @POST("twillio/sms.php")
////    Call<String> Book_taxi(@Body String body);
//
//
//    Call<Datamodel_result> Book_taxi(
//
//            @Field("sms") String sms,
//            @Field("email") String email,
//            @Field("from") String from,
//            @Field("name") String name
//    );


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("twillio/sms.php")
    Call<JsonObject> Book_taxi(@Body JsonObject locationPost);
}
