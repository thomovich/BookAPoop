package com.example.userexperience.models;



import java.io.Serializable;

public class DetailedListData implements Serializable {
    String title;
    String description;
    String Imageurl;
    String price;

    public DetailedListData(String title, String description, String imageurl, String price) {
        this.title = title;
        this.description = description;
        Imageurl = imageurl;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public String getPrice() {
        return price;
    }
}
