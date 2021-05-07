package com.example.shareus.ui.publicar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PublicarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PublicarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is sugerencias fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}