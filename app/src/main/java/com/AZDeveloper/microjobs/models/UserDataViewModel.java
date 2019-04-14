package com.AZDeveloper.microjobs.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDataViewModel extends ViewModel {
    private UserData userData;

    public LiveData<UserData> getInstance(){
        if(userData ==null){
            loadBalance();
        }
        return userData;
    }

    void loadBalance(){
        // In this function we have to load balance from the SERVER or anywhere else.

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("private").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                int balance, completed, pending;
                String uid = task.getResult().getId();
                balance = (int) task.getResult().get("balance");
                completed = (int) task.getResult().get("completedTask");
                pending = (int) task.getResult().get("pendingTask");
                userData = new UserData(uid,balance, completed, pending);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}
