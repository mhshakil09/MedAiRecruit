package com.example.medairecruit.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.medairecruit.api.TokenApi;
import com.example.medairecruit.databinding.ActivityHomeBinding;
import com.example.medairecruit.databinding.ActivitySignInBinding;
import com.example.medairecruit.ui.MainActivity;
import com.example.medairecruit.ui.sign_in.SignInActivity;
import com.example.medairecruit.ui.sign_in.SingInViewModel;
import com.example.medairecruit.utils.Helper;
import com.example.medairecruit.utils.SessionManager;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";

    private TokenApi tokenApi = new TokenApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initClickListener();
        initData();
    }

    private void initData() {
        binding.token.setText(SessionManager.getAccessToken());
    }

    private void initClickListener() {
        binding.logOutBtn.setOnClickListener(view -> {
            SessionManager.setIsSignedIn(this, false);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        binding.verifyTokenBtn.setOnClickListener(view -> {
            tokenApi.tokenVerify(this);
            initData();
        });

        binding.refreshTokenBtn.setOnClickListener(view -> {
            tokenApi.tokenRefresh(this);
            initData();
        });
    }
}