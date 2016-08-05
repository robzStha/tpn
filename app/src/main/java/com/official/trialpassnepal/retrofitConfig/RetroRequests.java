//package com.official.trialpassnepal.retrofitConfig;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class RetroRequests<T> {
//    public ResponseInterface responseInterface = null;
//
//    public void getResponse(Call<T> call, Class<T> type) {
//        call.enqueue(new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                if (response.isSuccessful()) {
//                    responseInterface.onProcessFinish(response.body(), 1);
//                } else {
//                    ResponseLogin login = ErrorUtils.parseError(response);
//                    responseInterface.onProcessFinish(login, 0);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//
//            }
//        });
//
//    }
//}