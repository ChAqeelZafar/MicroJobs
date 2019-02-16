package com.AZDeveloper.microjobs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.AZDeveloper.microjobs.Main2Activity;
import com.AZDeveloper.microjobs.MainActivity;
import com.AZDeveloper.microjobs.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    Button signoutBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, null);

        signoutBtn = view.findViewById(R.id.profile_btn_signout);
        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
