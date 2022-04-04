package com.example.wheregottimefind.data.pojo;

import java.util.ArrayList;
import java.util.Objects;

public class SearchResult {
    Vendor vendor;
    String productName;
    ArrayList<Integer> ratings;

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void addToRatings(int rating) {
        ratings.add(rating);
    }

    public double getAverageRating() {
        double averageRating = 0;
        for (Integer rating: ratings) {
            averageRating += rating;
        }
        return averageRating / ((double) ratings.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResult that = (SearchResult) o;
        return vendor.equals(that.vendor) && productName.equals(that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendor, productName);
    }

    public SearchResult(Vendor vendor, String productName) {
        this.vendor = vendor;
        this.productName = productName;
        this.ratings = new ArrayList<>();
    }
}
