package com.example.androidapplication.presentation.viewmodels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.androidapplication.data.repository.RemoteRepository;
import com.example.androidapplication.domain.entity.Result;
import com.example.androidapplication.presentation.uidata.PhotoViewData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class PhotoViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RemoteRepository remoteRepository;

    // MutableLiveData
    private MutableLiveData<List<PhotoViewData>> response = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public PhotoViewModel() {
        remoteRepository = new RemoteRepository();

    }

    //Load users and map into UI
    public void loadPhotoData() {
        //testTimber.wtf("Called loadPhotoData()");
        compositeDisposable.add(remoteRepository.loadImages()
                .map(data -> {
                    List<PhotoViewData> result = new ArrayList<>();
                    for (Result resultItem : data.getResults()) {

                        //Timber.wtf("CreatedAt info %s", resultItem.getCreatedAt());

                        PhotoViewData photoViewData = new PhotoViewData(
                                resultItem.getUser().getFirstName(),
                                resultItem.getCreatedAt(),
                                resultItem.getLikes(),
                                resultItem.getUrls().getSmall());
                        result.add(photoViewData);
                    }
                    Collections.shuffle(result, new Random());
                    return result;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> response.setValue(data),
                        error -> {
                            Timber.tag(TAG).e("loadPhotoList %s", error.getLocalizedMessage());
                            errorMessage.setValue(error.getMessage());
                        }
                )
        );
    }

    public MutableLiveData<List<PhotoViewData>> getResponse() {
        return response;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
