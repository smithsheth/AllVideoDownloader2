package com.video.allvideodownloaderbt.restapiclientsstuff;

import androidx.annotation.Keep;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


@Keep
public interface RetrofitApiInterface {
    @GET
    Call<JsonObject> getsnackvideoresult(@Url String url);

//    @FormUrlEncoded
//    @POST("1/fetch")
//    //@Headers("Content-Type: application/json")
//    Call<Html>getVideo_Info_tiktok(@FieldMap Map<String,String> params);
}
