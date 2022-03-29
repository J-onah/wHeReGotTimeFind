package com.example.wheregottimefind.backendAPI;

import com.example.wheregottimefind.pojo.FullReview;

public interface AsyncUpdate {
    void updateOnDataReceived(FullReview[] fullReviews);
}
