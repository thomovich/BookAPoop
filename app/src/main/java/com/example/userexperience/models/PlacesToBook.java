package com.example.userexperience.models;

import android.media.Rating;
import android.widget.ImageView;

public class PlacesToBook {
    String title;
    int imageUrl;
    String adress;
    int price;

    public PlacesToBook(String title, int imageUrl, String adress, int price, int distance, int rating) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.adress = adress;
        this.price = price;
        this.distance = distance;
        this.rating = rating;
    }

    int distance;
    int rating;


    public String getAdress() {
        return adress;
    }

    public int getPrice() {
        return price;
    }

    public int getDistance() {
        return distance;
    }

    public int getRating() {
        return rating;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getImageUrl() { return imageUrl; }





}
