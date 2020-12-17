//package com.example.androidapplication;
//
//import com.example.androidapplication.presentation.uidata.PhotoViewData;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Function;
//
//import timber.log.Timber;
//
//public class Mapper implements Function<PhotoData, List<PhotoViewData>> {
//
//
//    @Override
//    public List<PhotoViewData> apply(PhotoData photoData) {
//        List<PhotoViewData> result = new ArrayList<>();
//        for (Example example : photoData.getResults()) {
//            PhotoViewData photoViewData = new PhotoViewData(
//                    example.getUser().getFirstName(),
//                    example.getCreatedAt(),
//                    example.getLikes(),
//                    example.getUrls().getSmall()
//            );
//
//            result.add(photoViewData);
//
//        }
//        Timber.e("TIMBER RESULT ____________________________________________________________________" + result.toString());
//        return result;
//    }
//}
