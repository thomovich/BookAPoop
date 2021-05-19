package com.example.userexperience.models;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.util.Currency;

public class CreateAPlaceToBook {
    public String title;
    public int price;
    public LatLng address;
    public String description;
    public String imageUrl;
    public String straddress;


    public CreateAPlaceToBook(String title, int price, LatLng address, String description, String imageUrl, String straddress) {
        this.title = title;
        this.price = price;
        this.address = address;
        this.description = description;
        this.imageUrl = imageUrl;
        this.straddress = straddress;
    }
    public CreateAPlaceToBook(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LatLng getAddress() {
        return address;
    }

    public void setAddress(LatLng address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStraddress() {
        return straddress;
    }

    public void setStraddress(String straddress) {
        this.straddress = straddress;
    }
}
