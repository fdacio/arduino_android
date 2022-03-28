package br.com.daciosoftware.dsiot.ui.iot124;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Iot124ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Iot124ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}