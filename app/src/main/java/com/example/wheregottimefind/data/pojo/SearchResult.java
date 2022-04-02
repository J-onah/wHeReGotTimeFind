package com.example.wheregottimefind.data.pojo;

import java.util.Objects;

public class SearchResult {
    Vendor vendor;
    String productName;

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
    }
}
