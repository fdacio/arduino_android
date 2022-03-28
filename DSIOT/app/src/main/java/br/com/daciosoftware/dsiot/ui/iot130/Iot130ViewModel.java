package br.com.daciosoftware.dsiot.ui.iot130;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Iot130ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Iot130ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}