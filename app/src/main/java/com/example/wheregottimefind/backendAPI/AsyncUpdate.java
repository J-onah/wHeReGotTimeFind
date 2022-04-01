package com.example.wheregottimefind.backendAPI;

import com.example.wheregottimefind.data.pojo.FullReview;
import com.example.wheregottimefind.data.pojo.Vendor;

public interface AsyncUpdate<T> {
        void updateOnDataReceived(T array);
}
