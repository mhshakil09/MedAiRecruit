package com.example.medairecruit.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.medairecruit.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
    }
}