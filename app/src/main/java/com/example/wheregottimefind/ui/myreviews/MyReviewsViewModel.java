package com.example.wheregottimefind.ui.myreviews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyReviewsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyReviewsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is review fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}