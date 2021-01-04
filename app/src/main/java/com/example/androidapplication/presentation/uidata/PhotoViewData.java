package com.example.androidapplication.presentation.uidata;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotoViewData implements Parcelable {
    private String photographer;
    private String description;
    private int likes;
    private String photo;

    public PhotoViewData(String photographer, String description, int likes, String photo) {
        this.photographer = photographer;
        this.description = description;
        this.likes = likes;
        this.photo = photo;
    }

    public PhotoViewData(Parcel parcel) {
        photo = parcel.readString();
        photographer = parcel.readString();
        description = parcel.readString();
        likes = parcel.readInt();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(photo);
        dest.writeString(photographer);
        dest.writeString(description);
        dest.writeInt(likes);
    }

    public static final Parcelable.Creator<PhotoViewData> CREATOR = new Parcelable.Creator<PhotoViewData>() {

        @Override
        public PhotoViewData createFromParcel(Parcel in) {
            return new PhotoViewData(in);
        }

        @Override
        public PhotoViewData[] newArray(int size) {
            return new PhotoViewData[size];
        }

    };
}
