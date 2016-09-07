package com.official.trialpassnepal.retrofitConfig;

import com.official.trialpassnepal.objects.SyncData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by SlowhandJr. on 7/16/16.
 */
public interface ApiService {

    @GET("getData/krujtpn/{timestamp}")
    Call<SyncData> getSyncData(@Path("timestamp") String timestamp);

    @FormUrlEncoded
    @POST("userprofile/getData")
    Call<SyncData> getResponse(@Url String url,
                               @Field("user_name") String username,
                               @Field("user_email") String email,
                               @Field("privateKey") String key,
                               @Field("user_interest") String interest);

}
