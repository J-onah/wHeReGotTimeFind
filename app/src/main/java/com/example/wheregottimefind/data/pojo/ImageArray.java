package com.example.wheregottimefind.data.pojo;

public class ImageArray {
    String imagesData = "";

    public ImageArray(String imagesData) {
        this.imagesData = imagesData;
    }

    public void addImage(String img) {
        if (imagesData.isEmpty()) {
            imagesData += img;
        } else {
            imagesData += "," + img;
        }
    }
}
