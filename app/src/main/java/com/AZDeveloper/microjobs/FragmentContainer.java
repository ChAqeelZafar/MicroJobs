package com.AZDeveloper.microjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.AZDeveloper.microjobs.fragments.DashboardFragment;
import com.AZDeveloper.microjobs.fragments.HomeFragment;
import com.AZDeveloper.microjobs.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentContainer extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //When App start if there is user already signed in so Continue this Activity otherwise goto the Login page
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // UserData is signed in
            Intent i = new Intent(FragmentContainer.this, PhoneAuthActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
        }

        loadFragment(new HomeFragment());
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment!=null){

            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
            //getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, fragment).addToBackStack("home_fragment").commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;



        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;

        }

        return loadFragment(fragment);
    }
}
