package com.example.medairecruit.ui.health_information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.medairecruit.api.model.HealthInfoFirstModel;
import com.example.medairecruit.databinding.ActivityHealthInformationFirstBinding;
import com.example.medairecruit.databinding.ActivityHomeBinding;
import com.example.medairecruit.ui.profile.ProfileActivity;
import com.example.medairecruit.utils.Helper;

import timber.log.Timber;

public class HealthInformationFirstActivity extends AppCompatActivity {

    private ActivityHealthInformationFirstBinding binding;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";

    String tobacco;
    String smoking;
    String asthma;
    String covid;

    String vaccinated;
    String highBP;
    String highBPInFamily;
    String heartDisease;
    String heartDiseaseInFamily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHealthInformationFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        initClickListener();
        setText();
    }

    private void setText() {
        String text = "<font color=#574D4D>Tobacco consumption</font> <font color=#F80707>*</font> <font color=#574D4D>:</font>";
        binding.tobaccoTV.setText(Html.fromHtml(text));

        text = "<font color=#574D4D>Smoking cigarettes</font> <font color=#F80707>*</font> <font color=#574D4D>:</font>";
        binding.smokingTV.setText(Html.fromHtml(text));

        text = "<font color=#574D4D>Asthma</font> <font color=#F80707>*</font> <font color=#574D4D>:</font>";
        binding.asthmaTV.setText(Html.fromHtml(text));

        text = "<font color=#574D4D>Had COVID attack</font> <font color=#F80707>*</font> <font color=#574D4D>:</font>";
        binding.covidTV.setText(Html.fromHtml(text));

        text = "<font color=#574D4D>Vaccinated</font> <font color=#F80707>*</font> <font color=#574D4D>:</font>";
        binding.vaccinatedTV.setText(Html.fromHtml(text));

        text = "<font color=#574D4D>High blood pressure</font> <font color=#F80707>*</font> <font color=#574D4D>:</font>";
        binding.highBPTV.setText(Html.fromHtml(text));

        text = "<font color=#574D4D>High blood pressure \nin the history of family</font> <font color=#F80707>*</font> <font color=#574D4D>:</font>";
        binding.highBPInFamilyTV.setText(Html.fromHtml(text));

        text = "<font color=#574D4D>Heart desease</font> <font color=#F80707>*</font> <font color=#574D4D>:</font>";
        binding.heartDiseaseTV.setText(Html.fromHtml(text));

        text = "<font color=#574D4D>Heart disease \nin the history of family</font> <font color=#F80707>*</font> <font color=#574D4D>:</font>";
        binding.heartDiseaseInFamilyTV.setText(Html.fromHtml(text));
    }

    private void initClickListener() {
        binding.closeBtn.setOnClickListener(view -> {
            finish();
        });

        binding.submitBtn.setOnClickListener(view -> {
            if(verify()) {

                HealthInfoFirstModel model = new HealthInfoFirstModel(
                        tobacco, smoking, asthma, covid, vaccinated, highBP,
                        highBPInFamily, heartDisease, heartDiseaseInFamily
                );

                Timber.d("HealthIfoFirst: " + model);

                Bundle bundle = new Bundle();
                bundle.putParcelable("model", model);

                Intent intent = new Intent(getApplicationContext(), HealthInformationSecondActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private boolean verify() {
        RadioButton selectedRadioButton;
        RadioGroup radioGroup;
        int radioButtonID;

        //region Tobacco
        radioGroup = binding.tobaccoRG;
        radioButtonID = radioGroup.getCheckedRadioButtonId();
        if (radioButtonID != -1) {
            selectedRadioButton = findViewById(radioButtonID);
            tobacco = selectedRadioButton.getText().toString();
            Timber.d(tobacco);

        } else {
            Helper.toast(this, "Please select an option");
            return false;
        }
        //endregion

        //region smoking
        radioGroup = binding.smokingRG;
        radioButtonID = radioGroup.getCheckedRadioButtonId();
        if (radioButtonID != -1) {
            selectedRadioButton = findViewById(radioButtonID);
            smoking = selectedRadioButton.getText().toString();
            Timber.d(smoking);

        } else {
            Helper.toast(this, "Please select an option");
            return false;
        }
        //endregion

        //region asthma
        radioGroup = binding.asthmaRG;
        radioButtonID = radioGroup.getCheckedRadioButtonId();
        if (radioButtonID != -1) {
            selectedRadioButton = findViewById(radioButtonID);
            asthma = selectedRadioButton.getText().toString();
            Timber.d(asthma);

        } else {
            Helper.toast(this, "Please select an option");
            return false;
        }
        //endregion

        //region covid
        radioGroup = binding.covidRG;
        radioButtonID = radioGroup.getCheckedRadioButtonId();
        if (radioButtonID != -1) {
            selectedRadioButton = findViewById(radioButtonID);
            covid = selectedRadioButton.getText().toString();
            Timber.d(covid);

        } else {
            Helper.toast(this, "Please select an option");
            return false;
        }
        //endregion


        //region vaccinated
        radioGroup = binding.vaccinatedRG;
        radioButtonID = radioGroup.getCheckedRadioButtonId();
        if (radioButtonID != -1) {
            selectedRadioButton = findViewById(radioButtonID);
            vaccinated = selectedRadioButton.getText().toString();
            Timber.d(vaccinated);

        } else {
            Helper.toast(this, "Please select an option");
            return false;
        }
        //endregion

        //region highBP
        radioGroup = binding.highBPRG;
        radioButtonID = radioGroup.getCheckedRadioButtonId();
        if (radioButtonID != -1) {
            selectedRadioButton = findViewById(radioButtonID);
            highBP = selectedRadioButton.getText().toString();
            Timber.d(highBP);

        } else {
            Helper.toast(this, "Please select an option");
            return false;
        }
        //endregion

        //region highBPInFamily
        radioGroup = binding.highBPInFamilyRG;
        radioButtonID = radioGroup.getCheckedRadioButtonId();
        if (radioButtonID != -1) {
            selectedRadioButton = findViewById(radioButtonID);
            highBPInFamily = selectedRadioButton.getText().toString();
            Timber.d(highBPInFamily);

        } else {
            Helper.toast(this, "Please select an option");
            return false;
        }
        //endregion

        //region heartDisease
        radioGroup = binding.heartDiseaseRG;
        radioButtonID = radioGroup.getCheckedRadioButtonId();
        if (radioButtonID != -1) {
            selectedRadioButton = findViewById(radioButtonID);
            heartDisease = selectedRadioButton.getText().toString();
            Timber.d(heartDisease);

        } else {
            Helper.toast(this, "Please select an option");
            return false;
        }
        //endregion

        //region heartDiseaseInFamily
        radioGroup = binding.heartDiseaseInFamilyRG;
        radioButtonID = radioGroup.getCheckedRadioButtonId();
        if (radioButtonID != -1) {
            selectedRadioButton = findViewById(radioButtonID);
            heartDiseaseInFamily = selectedRadioButton.getText().toString();
            Timber.d(heartDiseaseInFamily);

        } else {
            Helper.toast(this, "Please select an option");
            return false;
        }
        //endregion


        return true;

    }


}