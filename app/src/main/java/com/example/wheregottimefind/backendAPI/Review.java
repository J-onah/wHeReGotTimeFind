package com.example.wheregottimefind.backendAPI;

import java.util.Arrays;

public class Review {
    private String userid;
    private String product_name;
    private int rating;
    private int units_purchased;
    private String unit;
    private double price_per_unit;
    private String[] tags;
    private String[] images;
    private String comments;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUnits_purchased() {
        return units_purchased;
    }

    public void setUnits_purchased(int units_purchased) {
        this.units_purchased = units_purchased;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "Review{" +
                "userid='" + userid + '\'' +
                ", product_name='" + product_name + '\'' +
                ", rating=" + rating +
                ", units_purchased=" + units_purchased +
                ", unit='" + unit + '\'' +
                ", price_per_unit=" + price_per_unit +
                ", tags=" + Arrays.toString(tags) +
                ", images=" + Arrays.toString(images) +
                ", comments='" + comments + '\'' +
                '}';
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice_per_unit() {
        return price_per_unit;
    }

    public void setPrice_per_unit(int price_per_unit) {
        this.price_per_unit = price_per_unit;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Review(String userid, String product_name, int rating, int units_purchased, String unit, int price_per_unit, String[] tags, String[] images, String comments) {
        this.userid = userid;
        this.product_name = product_name;
        this.rating = rating;
        this.units_purchased = units_purchased;
        this.unit = unit;
        this.price_per_unit = price_per_unit;
        this.tags = tags;
        this.images = images;
        this.comments = comments;
    }
}
