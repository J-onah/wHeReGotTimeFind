package com.example.wheregottimefind.data;

import android.util.Log;

import com.example.wheregottimefind.adapters.SearchResultAdapter;
import com.example.wheregottimefind.data.pojo.FullReview;
import com.example.wheregottimefind.data.pojo.SearchResult;
import com.example.wheregottimefind.data.pojo.Vendor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FullReviewData {
    ArrayList<FullReview> full_reviews;
    static FullReviewData instance;

    private FullReviewData() {
        full_reviews = new ArrayList<>();
    }

    public static FullReviewData getInstance() {
        if (instance == null) {
            instance = new FullReviewData();
        }
        return instance;
    }

    public void addFullReview(FullReview full_review) {
        full_reviews.add(full_review);
    }

    public ArrayList<FullReview> getAllReviews() {
        return full_reviews;
    }

    public void clearAll() {
        full_reviews = new ArrayList<>();
    }

    public List<SearchResult> getSearchResults(int ratings) {
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        for (FullReview full_review: full_reviews) {
            SearchResult searchResult = new SearchResult(full_review.getVendor(), full_review.getReview().getProduct_name());
            int existingSearchResultIdx = searchResults.indexOf(searchResult);
            if (existingSearchResultIdx == -1) {
                searchResult.addToRatings(full_review.getReview().getRating());
                searchResults.add(searchResult);
            } else {
                searchResults.get(existingSearchResultIdx).addToRatings(full_review.getReview().getRating());
            }
        }
        if (ratings == -1){
            Collections.sort(searchResults, (first, second)->{return (int) (first.getAverageRating()*10-second.getAverageRating()*10);
            });
        }
        else if (ratings == 1){
            Collections.sort(searchResults, (first,second)->{return (int) (second.getAverageRating()*10 -first.getAverageRating()*10);
            });
        }
        return searchResults;
    }

    public List<Vendor> getVendorsFromData() {
        Set<Vendor> vendors = new HashSet<>();
        for (FullReview full_review: full_reviews) {
            vendors.add(full_review.getVendor());
        }
        List<Vendor> res = new ArrayList<>(vendors);
        return res;
    }

    public List<FullReview> getReviewsByVendorIdAndItemName(int vendorId, String itemName) {
        ArrayList<FullReview> res = new ArrayList<>();
        for (FullReview full_review: full_reviews) {
            if (full_review.getVendor().getId() == vendorId && full_review.getReview().getProduct_name().equals(itemName)) {
                res.add(full_review);
            }
        }
        return res;
    }

    public List<FullReview> getReviewsByVendorId(int vendorId) {
        ArrayList<FullReview> res = new ArrayList<>();
        for (FullReview full_review: full_reviews) {
            if (full_review.getVendor().getId() == vendorId) {
                res.add(full_review);
            }
        }
        return res;
    }
}
