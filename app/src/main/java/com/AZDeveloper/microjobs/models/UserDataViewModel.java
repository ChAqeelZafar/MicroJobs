package com.AZDeveloper.microjobs.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserDataViewModel extends ViewModel {
    private User user;

    LiveData<User> getInstance(){
        if(user==null){
            user = new User();
            loadBalance();
        }
        return user;
    }

    void loadBalance(){
        // In this function we have to load balance from the SERVER or anywhere else.

    }
}
