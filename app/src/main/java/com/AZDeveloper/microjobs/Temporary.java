package com.AZDeveloper.microjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.AZDeveloper.microjobs.models.UserDataViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class Temporary extends AppCompatActivity {
    Button signoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        signoutBtn = findViewById(R.id.main_btn_signout);

        UserDataViewModel userDataViewModel = ViewModelProviders.of((FragmentActivity) getApplicationContext()).get(UserDataViewModel.class);




        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Temporary.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
