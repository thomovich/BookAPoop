package com.example.userexperience.UI;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.userexperience.models.PlacesToBook;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<PlacesToBook> selected = new MutableLiveData<PlacesToBook>();

    public void select(PlacesToBook placesToBook){
        selected.setValue(placesToBook);
    }

    public LiveData<PlacesToBook> getSelected(){
        return selected;
    }

}
