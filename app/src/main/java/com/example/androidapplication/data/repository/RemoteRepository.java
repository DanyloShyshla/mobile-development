package com.example.androidapplication.data.repository;

import com.example.androidapplication.data.RandomImageService;
import com.example.androidapplication.domain.entity.Example;
import com.example.androidapplication.domain.repository.DomainRepository;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRepository implements DomainRepository {

    private static final String BASE_URL = "http://206.81.22.134:3000/";
    private static final String ACCESS_KEY = "results";

    private final RandomImageService randomImageService;

    public RemoteRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getLoggingClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        randomImageService = retrofit.create(RandomImageService.class);
    }

    //Logging interceptor
    private OkHttpClient getLoggingClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        return client;
    }

    @Override
    public Single<Example> loadImages() {
        return randomImageService.loadImages(ACCESS_KEY);
    }
}
