package com.example.shareus.ui.sugerencias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SugerenciasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SugerenciasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
