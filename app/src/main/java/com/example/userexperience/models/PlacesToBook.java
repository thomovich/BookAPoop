package com.example.userexperience.models;



public class PlacesToBook{
    String desc;
    String geohash;
    double lat;
    double lng;
    String price;
    String stradr;
    String url;
    String title;
    double distancetouser;

    public PlacesToBook(String desc, String geohash, double lat, double lng, String price, String stradr, String url, String title) {
        this.desc = desc;
        this.geohash = geohash;
        this.lat = lat;
        this.lng = lng;
        this.price = price;
        this.stradr = stradr;
        this.url = url;
        this.title = title;
    }
    public PlacesToBook(){

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStradr() {
        return stradr;
    }

    public void setStradr(String stradr) {
        this.stradr = stradr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getDistancetouser() {
        return distancetouser;
    }

    public void setDistancetouser(double distancetouser) {
        this.distancetouser = distancetouser;
    }
}
