package com.example.wheregottimefind.data.pojo;

public class FullReview implements Comparable<FullReview>{
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

    @Override
    //by default will be rating for now
    public int compareTo(FullReview compare) {
        int cmp = compare.getReview().getRating()-this.getReview().getRating();
        //if equal rating sort by number of purchases instead
        if (cmp == 0) {
            return compare.getReview().getUnits_purchased() - this.getReview().getUnits_purchased();
        }
        return cmp;
    }
}
