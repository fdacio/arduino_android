package br.com.daciosoftware.dsiot.ui.iot140;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Iot140ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Iot140ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}