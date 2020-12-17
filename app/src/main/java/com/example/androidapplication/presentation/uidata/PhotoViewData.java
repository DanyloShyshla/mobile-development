package com.example.androidapplication.presentation.uidata;

public class PhotoViewData {
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
}
