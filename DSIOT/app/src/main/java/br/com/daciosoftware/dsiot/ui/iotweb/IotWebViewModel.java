package br.com.daciosoftware.dsiot.ui.iotweb;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IotWebViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IotWebViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}