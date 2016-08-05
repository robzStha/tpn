package com.official.trialpassnepal.retrofitConfig;

import com.official.trialpassnepal.objects.SyncData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by SlowhandJr. on 7/16/16.
 */
public interface ApiService {

    @GET("getData/krujtpn/{timestamp}")
    Call<SyncData> getSyncData(@Path("timestamp") String timestamp);

}
