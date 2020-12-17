package com.example.androidapplication.domain.repository;

import com.example.androidapplication.domain.entity.Example;

import io.reactivex.Single;

public interface DomainRepository {

    Single<Example> loadImages();

}
