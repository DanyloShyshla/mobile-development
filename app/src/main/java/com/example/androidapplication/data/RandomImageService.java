package com.example.androidapplication.data;


import com.example.androidapplication.domain.entity.Example;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RandomImageService {

//    @GET(".")
//    Single<PhotoData> loadImages(@Query("client_id") String results);
    @GET("{results}/")
    Single<Example> loadImages(@Path("results") String results);
}
