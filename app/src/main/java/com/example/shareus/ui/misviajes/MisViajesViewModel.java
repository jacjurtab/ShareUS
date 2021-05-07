package com.example.shareus.ui.misviajes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MisViajesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MisViajesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}