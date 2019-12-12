package hk.hkucs.yellowobjects.ui.ybmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class YBMapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public YBMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is YBMap fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}