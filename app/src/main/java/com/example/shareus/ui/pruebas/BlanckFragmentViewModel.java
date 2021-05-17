package com.example.shareus.ui.pruebas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BlanckFragmentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BlanckFragmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Pruebas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}