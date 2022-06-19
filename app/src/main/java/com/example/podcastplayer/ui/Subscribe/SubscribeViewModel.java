package com.example.podcastplayer.ui.Subscribe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SubscribeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SubscribeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}