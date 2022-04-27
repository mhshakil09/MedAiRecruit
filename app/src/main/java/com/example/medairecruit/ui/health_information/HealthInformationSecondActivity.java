package com.example.medairecruit.ui.health_information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medairecruit.api.model.HealthInfoFirstModel;
import com.example.medairecruit.api.model.HealthInfoSecondModel;
import com.example.medairecruit.api.model.PatientRegistrationProfileModel;
import com.example.medairecruit.databinding.ActivityHealthInformationSecondBinding;
import com.example.medairecruit.databinding.ActivityHomeBinding;
import com.example.medairecruit.ui.MainActivity;
import com.example.medairecruit.ui.home.HomeActivity;
import com.example.medairecruit.ui.profile.ProfileActivity;
import com.example.medairecruit.ui.symptom_checker.SymptomCheckerActivity;
import com.example.medairecruit.utils.Helper;
import com.example.medairecruit.utils.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class HealthInformationSecondActivity extends AppCompatActivity {

    private ActivityHealthInformationSecondBinding binding;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";

    private HealthInfoFirstModel model = new HealthInfoFirstModel();
    private HealthInfoSecondModel healthInfoSecondModel;

    List<String> allergiesList = new ArrayList<>();
    String allergiesOtherDetails;
    List<String> regularMedicinesList = new ArrayList<>();


    String[] menopausalStatusList= {"Select", "Yes", "No", "NA"};

    String allergy = "";
    String regularMed;
    String menopausalStatus;
    String diabetes;
    String diabetesHis;
    String pregnancy;
    String recreationalDrug;


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
//        initFetchData();
        initData();
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

    private void initData() {
        fetchMenopausalStatus();
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
            initFetchData();
            if (verify()) {

//                model.getTobacco();
                healthInfoSecondModel = new HealthInfoSecondModel(
                        SessionManager.getPatientId(),
                        model.getTobacco(), model.getSmoking(), model.getAsthma(),
                        model.getCovid(), model.getVaccinated(),
                        model.getHighBP(), model.getHighBPInFamily(),
                        model.getHeartDisease(), model.getHeartDiseaseInFamily(),


                        allergy, regularMed, menopausalStatus,
                        diabetes, diabetesHis, pregnancy, recreationalDrug
                        );

                Timber.d(healthInfoSecondModel.toString());
                submitData(healthInfoSecondModel);

                Intent intent = new Intent(getApplicationContext(), SymptomCheckerActivity.class);
                startActivity(intent);
            }
        });
    }

    private Boolean verify() {
        initFetchData();
        if (allergiesList.isEmpty()) {
            Helper.toast(getApplicationContext(), "Please select at least one options from Allergies");
            allergy = "No";
            return false;
        }
        allergy = "Yes";
        if (binding.allergiesOthers.isChecked() && allergiesOtherDetails.length() < 5) {
            Helper.toast(getApplicationContext(), "Please describe in details");
            return false;
        }
        if (regularMedicinesList.isEmpty()) {
            Helper.toast(getApplicationContext(), "Please select at least one options from Regular Medicines");
            regularMed = "No";
            return false;
        }
        regularMed = "Yes";

        int menopausalStatusSelected = binding.spinnerMenopause.getSelectedItemPosition();
        if (menopausalStatusSelected == 0) {
            Helper.toast(getApplicationContext(), "Please select a Menopausal Status");
            return false;
        }
        menopausalStatus = binding.spinnerMenopause.getSelectedItem().toString();

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

    private void fetchMenopausalStatus() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, menopausalStatusList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerMenopause.setAdapter(spinnerArrayAdapter);
    }

    public void postRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://54.179.12.36/api/v1/all_user/medical_history";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Timber.d("------------------------------");
                    Timber.d(jsonObject.toString());
                    Timber.d("------------------------------");
                    if (jsonObject.getString("code").toString().matches("202")) {
                        Intent intent = new Intent(getApplicationContext(), SymptomCheckerActivity.class);
                        startActivity(intent);
                    } else {
                        Helper.toast(getApplicationContext(), "Something went wrong, please try again");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Timber.d("POST DATA : unable to Parse Json");
//                    post_response_text.setText("POST DATA : unable to Parse Json");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Timber.d("Post Data : Response Failed");
                Helper.toast(getApplicationContext(), "Something went wrong, please try again");
//                post_response_text.setText("Post Data : Response Failed");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("patient_id", SessionManager.getPatientId());

                params.put("tobacco", healthInfoSecondModel.getTobacco());
                params.put("smoking", healthInfoSecondModel.getSmoking());
                params.put("asthma", healthInfoSecondModel.getAsthma());
                params.put("covid_status", healthInfoSecondModel.getCovid());
                params.put("covid_vaccine_status", healthInfoSecondModel.getVaccinated());
                params.put("high_bp", healthInfoSecondModel.getHighBP());

                params.put("cardiac_dis", healthInfoSecondModel.getHeartDisease());
                params.put("cardiac_his", healthInfoSecondModel.getHeartDiseaseInFamily());

                params.put("allergy", healthInfoSecondModel.getAllergy());
                params.put("regular_med", healthInfoSecondModel.getRegularMed());
                params.put("menopausal_status", healthInfoSecondModel.getMenopausalStatus());

                params.put("diabetes", healthInfoSecondModel.getDiabetes());
                params.put("diabetes_his", healthInfoSecondModel.getDiabetesHis());
                params.put("pregnancy", healthInfoSecondModel.getPregnancy());
                params.put("recreational_drug", healthInfoSecondModel.getRecreationalDrug());

                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }


    public void submitData( HealthInfoSecondModel healthInfoSecondModel) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://54.179.12.36/api/v1/all_user/medical_history";
        Timber.d("sent data %s", healthInfoSecondModel.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, healthInfoSecondModel,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Timber.d("------------------------------");
                            Timber.d(String.valueOf(response));
                            Timber.d("------------------------------");
                            Timber.d( "error -> " + response.getString("error"));
                            Timber.d( "code -> " + response.getString("code"));
//                            SessionManager.setAccessToken(getApplicationContext(), response.getString("access"));
//                            SessionManager.setPatientId(getApplicationContext(), "");
//                        hideProgressDialog();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            Timber.d("POST DATA : unable to Parse Json");

//                        hideProgressDialog();
//                    post_response_text.setText("POST DATA : unable to Parse Json");
                        }
//                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Timber.d("Post Data : Response Failed");
                Timber.d(error);
                tokenRefresh();
                Timber.d(error.getMessage());
//                hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + SessionManager.getAccessToken());
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        requestQueue.add(jsonObjReq);

    }


    public void tokenRefresh() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url="http://54.179.12.36/api/v1/accounts/token/refresh";

        Timber.d("Refresh Token -> "+SessionManager.getRefreshToken());
        Timber.d("Access Token -> "+SessionManager.getAccessToken());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Timber.d("------------------------------");
                    Timber.d(jsonObject.toString());
                    Timber.d("------------------------------");
                    Timber.d(jsonObject.getString("access"));

                    Timber.d("Access Token changed");
                    Helper.toast(getApplicationContext(), "Token changed");

                    SessionManager.setAccessToken(getApplicationContext(), jsonObject.getString("access"));
//                    binding.token.setText(jsonObject.getString("access"));
//                    binding.token.setText(SessionManager.getAccessToken());

                }
                catch (Exception e){
                    e.printStackTrace();
                    Timber.d("POST DATA : unable to Parse Json");

//                    post_response_text.setText("POST DATA : unable to Parse Json");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Timber.d("Post Data : Response Failed");
                Timber.d(error);
                Timber.d("Refresh Token Invalidate");
                finish();
                SessionManager.setIsSignedIn(getApplicationContext(), false);
                SessionManager.setRefreshToken(getApplicationContext(), "");
                SessionManager.setAccessToken(getApplicationContext(), "");
                Helper.toast(getApplicationContext(), "Please sign in again");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("refresh", SessionManager.getRefreshToken());
                return params;
            }

//            @Override
//            public Map<String,String> getHeaders() throws AuthFailureError {
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("Content-Type","application/json");
//                return params;
//            }
        };

        requestQueue.add(stringRequest);
    }
}