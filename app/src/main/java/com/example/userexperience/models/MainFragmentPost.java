package com.example.userexperience.models;

public class MainFragmentPost {
    String title;
    String content;
    String imgurl;

    public MainFragmentPost(String title, String content, String imgurl) {
        this.title = title;
        this.content = content;
        this.imgurl = imgurl;
    }

    public MainFragmentPost(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
