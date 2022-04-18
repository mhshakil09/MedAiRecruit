package com.example.medairecruit.ui.health_information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.example.medairecruit.api.model.HealthInfoFirstModel;
import com.example.medairecruit.databinding.ActivityHealthInformationSecondBinding;
import com.example.medairecruit.databinding.ActivityHomeBinding;
import com.example.medairecruit.ui.MainActivity;
import com.example.medairecruit.ui.home.HomeActivity;
import com.example.medairecruit.ui.symptom_checker.SymptomCheckerActivity;
import com.example.medairecruit.utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class HealthInformationSecondActivity extends AppCompatActivity {

    private ActivityHealthInformationSecondBinding binding;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";

    private HealthInfoFirstModel model = new HealthInfoFirstModel();

    List<String> allergiesList = new ArrayList<>();
    String allergiesOtherDetails;
    List<String> regularMedicinesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHealthInformationSecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //parsing data from previous activity
        Bundle bundle = getIntent().getExtras();
        model = bundle.getParcelable("model");

        getSupportActionBar().hide();

        initClickListener();
        initFetchData();
        setText();
    }

    private void setText() {
        String text = "<font color=#574D4D>Allergies</font> <font color=#F80707>*</font> <font color=#574D4D>:</font>";
        binding.allergiesTV.setText(Html.fromHtml(text));

        text = "<font color=#574D4D>Regular medicines</font> <font color=#F80707>*</font> <font color=#574D4D>:</font>";
        binding.regularMedicinesTV.setText(Html.fromHtml(text));

        text = "<font color=#574D4D>Menopause</font> <font color=#F80707>*</font> <font color=#574D4D>:</font>";
        binding.menopauseTV.setText(Html.fromHtml(text));

    }

    private void initClickListener() {
        binding.backBtn.setOnClickListener(view -> {
            finish();
        });

        binding.closeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            finishAffinity();
            startActivity(intent);
        });

        binding.allergiesOthers.setOnClickListener(view -> {
            if (binding.allergiesOthers.isChecked()) {
                binding.allergiesOthersET.setVisibility(View.VISIBLE);
            } else {
                binding.allergiesOthersET.setVisibility(View.GONE);
            }
        });

        binding.submitBtn.setOnClickListener(view -> {
            //all data is in hand, just need to send in API
            if (verify()) {
                Intent intent = new Intent(getApplicationContext(), SymptomCheckerActivity.class);
                startActivity(intent);
            }
        });
    }

    private Boolean verify() {
        initFetchData();
        if (allergiesList.isEmpty()) {
            Helper.toast(getApplicationContext(), "Please select at least one options from Allergies");
            return false;
        }
        if (binding.allergiesOthers.isChecked() && allergiesOtherDetails.length() < 5) {
            Helper.toast(getApplicationContext(), "Please describe in details");
            return false;
        }
        if (regularMedicinesList.isEmpty()) {
            Helper.toast(getApplicationContext(), "Please select at least one options from Regular Medicines");
            return false;
        }
        return true;
    }

    private void initFetchData() {
        if (binding.dust.isChecked()) {
            allergiesList.add("dust");
        }
        if (binding.food.isChecked()) {
            allergiesList.add("food");
        }
        if (binding.drugs.isChecked()) {
            allergiesList.add("Drugs");
        }
        if (binding.material.isChecked()) {
            allergiesList.add("Material");
        }
        if (binding.insects.isChecked()) {
            allergiesList.add("Insects");
        }
        if (binding.allergiesOthers.isChecked()) {
            allergiesList.add("Others");
            allergiesOtherDetails = binding.allergiesOthersET.getText().toString();
        }

        if (binding.nitroglycerin.isChecked()) {
            regularMedicinesList.add("Nitroglycerin");
        }
        if (binding.glucose.isChecked()) {
            regularMedicinesList.add("Glucose");
        }
        if (binding.insulin.isChecked()) {
            regularMedicinesList.add("Insulin");
        }
        if (binding.inhaler.isChecked()) {
            regularMedicinesList.add("Inhaler");
        }
        if (binding.epinephrine.isChecked()) {
            regularMedicinesList.add("Epinephrine");
        }
        if (binding.statin.isChecked()) {
            regularMedicinesList.add("Statin");
        }
        if (binding.regularMedicinesOther.isChecked()) {
            regularMedicinesList.add("Others");
        }
    }
}