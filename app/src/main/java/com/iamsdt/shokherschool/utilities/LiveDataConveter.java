package com.iamsdt.shokherschool.utilities;

import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by Shudipto Trafder on 12/8/2017.
 * at 5:36 PM
 */

public class LiveDataConveter {

    private List<String> strings;

    public LiveDataConveter(List<String> strings) {
        this.strings = strings;
    }

    void converter(){

        final LiveData<List<String>> listLiveData = new LiveData<List<String>>() {
            @Override
            protected void postValue(List<String> value) {
                super.postValue(value);
                value = strings;
            }
        };

    }
}
