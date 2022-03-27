package com.example.wheregottimefind.backendAPI;

public class FullReview {
    private Vendor vendor;

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public FullReview(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        return "FullReview{" +
                "vendor=" + vendor +
                '}';
    }
}
