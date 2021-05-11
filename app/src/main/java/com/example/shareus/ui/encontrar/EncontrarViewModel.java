package com.example.shareus.ui.encontrar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EncontrarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EncontrarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");


    }

    public LiveData<String> getText() {
        return mText;
    }
}