package com.example.yellowobjects.ui.poster_page;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PosterPageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PosterPageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is PosterPage fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}