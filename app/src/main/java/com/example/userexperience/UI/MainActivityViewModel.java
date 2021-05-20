package com.example.userexperience.UI;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.userexperience.Data.UserRepository;
import com.example.userexperience.models.CreateAPlaceToBook;
import com.google.firebase.auth.FirebaseUser;

public class MainActivityViewModel extends AndroidViewModel {

    private final UserRepository userRepository;


    public MainActivityViewModel(Application app){
        super(app);
        userRepository = UserRepository.getInstance(app);

    }


    public void init(){
        String userId = userRepository.getCurrentUser().getValue().getUid();
    }

    public void signOut(){
        userRepository.signOut();
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }



}
