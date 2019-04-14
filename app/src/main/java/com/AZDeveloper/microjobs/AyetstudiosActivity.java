package com.AZDeveloper.microjobs;

import android.os.Bundle;

import com.ayetstudios.publishersdk.AyetSdk;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AyetstudiosActivity extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayetstudios);

        btn = findViewById(R.id.aye_button);

        AyetSdk.init(getApplication(), FirebaseAuth.getInstance().getUid());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AyetSdk.isInitialized()) {
                    AyetSdk.showOfferwall(getApplication());
                } else {
                    Toast.makeText(AyetstudiosActivity.this, "AyetSdk not initialized", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
