package com.example.medairecruit.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medairecruit.api.model.PatientRegistrationProfileModel;
import com.example.medairecruit.databinding.ActivityProfileBinding;
import com.example.medairecruit.ui.MainActivity;
import com.example.medairecruit.ui.health_information.HealthInformationFirstActivity;
import com.example.medairecruit.utils.Helper;
import com.example.medairecruit.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";

    PatientRegistrationProfileModel patientRegistrationProfileModel;

    String firstName, lastName, mobileNumber;
    String[] genderList = {"Select", "Male", "Female", "Secret"};
    String gender;
    String[] ageList = {"Select", "20", "40", "60"};
    int age;
    String[] maritalStatusList = {"Select", "single", "married", "divorced", "widow", "widower", "prefer no to say", "unknown", "Don't Know"};
    String maritalStatus;
    String[] religionList = {"Select", "Islam", "Hindu", "Christian", "Buddhist"};
    String religion;
    String[] educationList = {"Select", "SSC", "HSC", "BSc", "MSc"};
    String education;
    String[] occupationList = {"Select", "Student", "Business", "Service Holder"};
    String occupation;
    String[] ethnicityList = {"Select", "Bangladeshi", "British", "African"};
    String ethnicity;
    String[] countryList = {"Select", "Bangladesh", "India", "Pakistan", "China", "Japan"};
    String country;
    String[] regionList = {"Select", "Asia", "Europe", "Africa"};
    String region;

    List<String> allSymptomsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        initData();
        initClickListener();
        tokenVerify();
    }

    private void initData() {
        fetchGender();
        fetchAge();
        fetchMaritalStatus();
        fetchReligion();
        fetchEducation();
        fetchOccupation();
        fetchEthnicity();
        fetchCountry();
        fetchRegion();
    }

    private void initClickListener() {
        binding.backBtn.setOnClickListener(view -> {
            SessionManager.setIsSignedIn(getApplicationContext(), false);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        binding.submitBtn.setOnClickListener(view -> {
            Helper.toast(getApplicationContext(), "submit pressed");
            if (verify()) {

                patientRegistrationProfileModel = new PatientRegistrationProfileModel(
                        mobileNumber, firstName, lastName, gender, 20, maritalStatus,
                        "", "Student", "white", country, region,
                        "", "", "Kushtia", "", 5000,
                        "Don't Know", "Real", "Yes", "");

//                Timber.d(patientRegistrationProfileModel.toString());

//                submitProfileData(patientRegistrationProfileModel);
                //TODO testing the following
//                getAllSymptoms();

                tokenVerify();
                SessionManager.setIsProfileComplete(getApplicationContext(),true);
                Intent intent = new Intent(getApplicationContext(), HealthInformationFirstActivity.class);
                startActivity(intent);

            }
        });
    }

    private boolean verify() {

        //region Mandatory Fields
        firstName = binding.firstNameET.getText().toString();
        if (firstName.isEmpty()) {
            Helper.toast(getApplicationContext(), "Please enter First Name");
            binding.firstNameET.requestFocus();
            binding.scrollView.scrollTo(0, 0);
            return false;
        }

        lastName = binding.lastNameET.getText().toString();
        if (lastName.isEmpty()) {
            Helper.toast(getApplicationContext(), "Please enter Last Name");
            binding.lastNameET.requestFocus();
            binding.scrollView.scrollTo(0, 0);
            return false;
        }

        int genderSelected = binding.spinnerGender.getSelectedItemPosition();
        if (genderSelected == 0) {
            Helper.toast(getApplicationContext(), "Please select a Gender");
            return false;
        }
        gender = binding.spinnerGender.getSelectedItem().toString();

        int ageSelected = binding.spinnerAge.getSelectedItemPosition();
        if (ageSelected == 0) {
            Helper.toast(getApplicationContext(), "Please select a Gender");
            return false;
        }
        age = Integer.parseInt(binding.spinnerAge.getSelectedItem().toString());

        mobileNumber = binding.mobileNumberET.getText().toString();
        if (mobileNumber.isEmpty()) {
            Helper.toast(getApplicationContext(), "Please enter Mobile number");
            binding.mobileNumberET.requestFocus();
            return false;
        }

        int countrySelected = binding.spinnerCountry.getSelectedItemPosition();
        if (countrySelected == 0) {
            Helper.toast(getApplicationContext(), "Please select a Country");
            return false;
        }
        country = binding.spinnerCountry.getSelectedItem().toString();

        int regionSelected = binding.spinnerCountry.getSelectedItemPosition();
        if (regionSelected == 0) {
            Helper.toast(getApplicationContext(), "Please select a Region");
        }
        region = binding.spinnerRegion.getSelectedItem().toString();
        //endregion

        //region Optional Fields
        if (binding.spinnerMaritalStatus.getSelectedItemPosition() == 0) {
            maritalStatus = "";
        } else {
            maritalStatus = binding.spinnerMaritalStatus.getSelectedItem().toString();
        }

        if (binding.spinnerReligion.getSelectedItemPosition() == 0) {
            religion = "";
        } else {
            religion = binding.spinnerReligion.getSelectedItem().toString();
        }

        if (binding.spinnerEducation.getSelectedItemPosition() == 0) {
            education = "";
        } else {
            education = binding.spinnerEducation.getSelectedItem().toString();
        }

        if (binding.spinnerOccupation.getSelectedItemPosition() == 0) {
            occupation = "";
        } else {
            occupation = binding.spinnerOccupation.getSelectedItem().toString();
        }

        if (binding.spinnerEthnicity.getSelectedItemPosition() == 0) {
            ethnicity = "";
        } else {
            ethnicity = binding.spinnerEthnicity.getSelectedItem().toString();
        }
        //endregion


        return true;
    }


    private void fetchGender() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, genderList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerGender.setAdapter(spinnerArrayAdapter);
    }

    private void fetchAge() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, ageList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAge.setAdapter(spinnerArrayAdapter);
    }

    private void fetchMaritalStatus() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, maritalStatusList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerMaritalStatus.setAdapter(spinnerArrayAdapter);
    }

    private void fetchReligion() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, religionList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerReligion.setAdapter(spinnerArrayAdapter);
    }

    private void fetchEducation() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, educationList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerEducation.setAdapter(spinnerArrayAdapter);
    }

    private void fetchOccupation() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, occupationList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerOccupation.setAdapter(spinnerArrayAdapter);
    }

    private void fetchEthnicity() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, ethnicityList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerEthnicity.setAdapter(spinnerArrayAdapter);
    }

    private void fetchCountry() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, countryList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCountry.setAdapter(spinnerArrayAdapter);
    }

    private void fetchRegion() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, regionList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRegion.setAdapter(spinnerArrayAdapter);
    }



//    public void submitProfileData() {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        String url="http://54.179.12.36/api/v1/all_user/patient_registration";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //let's parse json data
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    Timber.d("------------------------------");
//                    Timber.d(jsonObject.toString());
//                    Timber.d("------------------------------");
////                    SessionManager.setAccessToken(getApplicationContext(), "");
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                    Timber.d("POST DATA : unable to Parse Json");
//
////                    post_response_text.setText("POST DATA : unable to Parse Json");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Timber.d("Post Data : Response Failed");
//                Timber.d(error);
////                Timber.d("Refresh Token Invalidate");
////                finish();
////                SessionManager.setIsSignedIn(getApplicationContext(), false);
////                SessionManager.setRefreshToken(getApplicationContext(), "");
////                SessionManager.setAccessToken(getApplicationContext(), "");
////                Helper.toast(getApplicationContext(), "Please sign in again");
////                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//
//            }
//        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("refresh", SessionManager.getRefreshToken());
////                params.put("refresh", SessionManager.getRefreshToken());
//                return params;
//            }
//
//            @Override
//            public Map<String,String> getHeaders() throws AuthFailureError {
//                Map<String,String> headers = new HashMap<>();
//                headers.put("Authorization", "Bearer " + SessionManager.getAccessToken());
//                return headers;
//            }
//        };
//
//        requestQueue.add(stringRequest);
//    }

    //testing
    public void submitProfileData( PatientRegistrationProfileModel patientRegistrationProfileModel) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://54.179.12.36/api/v1/all_user/patient_registration";
        Timber.d("sent data %s", patientRegistrationProfileModel.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, patientRegistrationProfileModel,
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

    public void tokenVerify() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url="http://54.179.12.36/api/v1/accounts/token/verify";

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

                    Helper.toast(getApplicationContext(), "token is valid");

//                    SessionManager.setRefreshToken(context, jsonObject.getString("refresh"));
//                    SessionManager.setAccessToken(context, jsonObject.getString("access"));

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
                Timber.d("Token Invalidate");
                tokenRefresh();
//                post_response_text.setText("Post Data : Response Failed");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("token", SessionManager.getAccessToken());
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
//        Timber.d(isValid[0]);
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

//    for testing purposes
//    public void getAllSymptoms() {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        String url="http://54.179.12.36/api/v1/all_user/symptoms/get_all_symptoms?lang=lang%3Deng";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //let's parse json data
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    Timber.d("------------------------------");
////                    Timber.d(jsonObject.toString());
//                    Timber.d("------------------------------");
//
//                    Helper.toast(getApplicationContext(), "token is valid");
//
////                    Timber.d(jsonObject.getString("symptoms"));
//
//                    JSONArray arr = jsonObject.getJSONArray("symptoms");
//                    JSONObject element;
//                    String label = "";
//                    for(int i = 0; i < arr.length(); i++){
//                        element = arr.getJSONObject(i);
//                        label = element.getString("label");
//                        allSymptomsList.add(label);
////                        Timber.d(" " + allSymptomsObjectList);
//                    }
//
//                    Timber.d("+++++++++++++++++++++++++");
////                    Timber.d(" " + allSymptomsObjectList);
//                    Timber.d("+++++++++++++++++++++++++");
//                    Timber.d(" " + allSymptomsList.get(1));
//                    Timber.d(" " + label);
//
//
////                    SessionManager.setRefreshToken(getApplicationContext(), jsonObject.getString("refresh"));
////                    SessionManager.setAccessToken(context, jsonObject.getString("access"));
//
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                    Timber.d("POST DATA : unable to Parse Json");
//
////                    post_response_text.setText("POST DATA : unable to Parse Json");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Timber.d("Post Data : Response Failed");
//                Timber.d(error);
////                Timber.d("Token Invalidate");
////                tokenRefresh();
////                post_response_text.setText("Post Data : Response Failed");
//            }
//        }){
////            @Override
////            protected Map<String,String> getParams(){
////                Map<String,String> params=new HashMap<String, String>();
////                params.put("token", SessionManager.getAccessToken());
////                return params;
////            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "Bearer " + SessionManager.getAccessToken());
//                headers.put("Content-Type","application/x-www-form-urlencoded");
//                return headers;
//            }
//
//
//        };
//
//        requestQueue.add(stringRequest);
////        Timber.d(isValid[0]);
//    }

}