package br.com.daciosoftware.dsiot.ui.iot125;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Iot125ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Iot125ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}