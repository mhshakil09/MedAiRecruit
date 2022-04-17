package com.example.medairecruit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.medairecruit.databinding.ActivityMainBinding;
import com.example.medairecruit.ui.home.HomeActivity;
import com.example.medairecruit.ui.profile.ProfileActivity;
import com.example.medairecruit.ui.sign_in.SignInActivity;
import com.example.medairecruit.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SessionManager.init(this);

        initClickListener();

        checkSignedIn();
    }

    private void initClickListener() {
        binding.signInBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        });
    }

    private void checkSignedIn() {
        if (SessionManager.getIsSignedIn()) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
            finish();
        }
    }
}