package com.example.wheregottimefind.data.pojo;

public class FullReview {
    private Vendor vendor;
    private Review review;

    public FullReview(Vendor vendor, Review review) {
        this.vendor = vendor;
        this.review = review;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        return "FullReview{" +
                "vendor=" + vendor +
                ", review=" + review +
                '}';
    }
}
