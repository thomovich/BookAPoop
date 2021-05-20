package com.example.userexperience.UI;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.userexperience.Data.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class SigninViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public SigninViewModel(Application app){
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }
}
