package com.official.trialpassnepal.retrofitConfig;

import com.official.trialpassnepal.utils.CommonDef;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static Retrofit retrofit = null;
    private static RetrofitSingleton retrofitSingleton = null;
    private static ApiService apiService = null;

    private RetrofitSingleton() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder().
                baseUrl(CommonDef.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static ApiService getApiService() {
        if (retrofitSingleton == null) {
            retrofitSingleton = new RetrofitSingleton();
        }
        return apiService;
    }

    public static Retrofit getRetrofit() {
        if (retrofitSingleton == null) {
            retrofitSingleton = new RetrofitSingleton();
        }
        return retrofit;
    }
    /*public <T> T getService(Class<T> type) {
        return retrofit.create(type);
    }*/
}