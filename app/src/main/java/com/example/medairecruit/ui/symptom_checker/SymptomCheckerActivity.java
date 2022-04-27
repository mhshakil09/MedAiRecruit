package com.example.medairecruit.ui.symptom_checker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medairecruit.R;
import com.example.medairecruit.databinding.ActivitySymptomCheckerBinding;
import com.example.medairecruit.ui.MainActivity;
import com.example.medairecruit.ui.home.HomeActivity;
import com.example.medairecruit.ui.profile.ProfileActivity;
import com.example.medairecruit.utils.Helper;
import com.example.medairecruit.utils.SessionManager;
import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class SymptomCheckerActivity extends AppCompatActivity {

    private ActivitySymptomCheckerBinding binding;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";

    private SymptomCheckerSuggestionAdapter dataAdapter = new SymptomCheckerSuggestionAdapter();

    List<String> suggestedWordList = new ArrayList<>();
    String selectedSuggestions = "";

    List<String> searchedWordList = new ArrayList<>();
    String selectedSearched = "";

    List<JSONObject> allSymptomsObjectList = new ArrayList<JSONObject>();
    List<String> allSymptomsList = new ArrayList<>();

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySymptomCheckerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        initSelectedRecyclerView();
//        initSearchedRecyclerView();
        initData();
        initClickListener();
        getAllSymptoms();
//        initAutoComplete();
    }

    private void initClickListener() {

        binding.closeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            finishAffinity();
            startActivity(intent);
        });

        binding.submitBtn.setOnClickListener(view -> {
            //TODO uncomment the following
            showCustomDialog();
            //TODO comment the following
//            getAllSymptoms();
        });
    }

    private void initData() {
        suggestedWordList.add("Fever");
        suggestedWordList.add("Cough");
        suggestedWordList.add("Cramping");
        suggestedWordList.add("Weight loss");
        suggestedWordList.add("Tenderness");

        searchedWordList.add("Stomach pain");
        searchedWordList.add("Stomach ache");
        searchedWordList.add("Dull stomach pain");
    }

//    private void initSearchedRecyclerView() {
//
//        RecyclerView recyclerView = findViewById(R.id.recyclerViewSearched);
//        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
//        dataAdapter = new SymptomCheckerSuggestionAdapter(this, searchedWordList);
//        dataAdapter.setClickListener((view, position) ->
//                //Helper.toast(getApplicationContext(),"clicked items position is "+position+" -> "+suggestedWordList.get(position))
//                selectedSearched = searchedWordList.get(position)
//        );
//        recyclerView.setAdapter(dataAdapter);
//    }

    private void initSelectedRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recyclerViewSuggested);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));
        dataAdapter = new SymptomCheckerSuggestionAdapter(this, suggestedWordList);
        dataAdapter.setClickListener((view, position) ->
                //Helper.toast(getApplicationContext(),"clicked items position is "+position+" -> "+suggestedWordList.get(position))
                selectedSuggestions = suggestedWordList.get(position)
        );
        recyclerView.setAdapter(dataAdapter);
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.custom_dialog);

        //Initializing the views of the dialog.
        Button submitButton = dialog.findViewById(R.id.submitBtn);
        Button startOverButton = dialog.findViewById(R.id.startOverBtn);

        startOverButton.setOnClickListener(view -> {
            dialog.dismiss();
            finishAffinity();
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });

        submitButton.setOnClickListener(view -> {
            dialog.dismiss();
            finishAffinity();
            SessionManager.setIsSignedIn(getApplicationContext(), false);
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });

        dialog.show();
    }

    private void initAutoComplete() {
        Chip chip = new Chip( this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, allSymptomsList);
        binding.autoCompleteET.setThreshold(1);
        binding.autoCompleteET.setAdapter(adapter);
        binding.autoCompleteET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                chip.setText(adapter.getItem(i));
                chip.setCloseIconVisible(true);
                binding.selectedSymptomsCG.addView(chip);

                binding.autoCompleteET.getText().clear();

                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.selectedSymptomsCG.removeView(chip);
                    }
                });

                Helper.toast(getApplicationContext(), adapter.getItem(i));
            }
        });
    }

    public void getAllSymptoms() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url="http://54.179.12.36/api/v1/all_user/symptoms/get_all_symptoms?lang=lang%3Deng";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Timber.d("------------------------------");
//                    Timber.d(jsonObject.toString());
                    Timber.d("------------------------------");

                    Helper.toast(getApplicationContext(), "token is valid");

//                    Timber.d(jsonObject.getString("symptoms"));

                    JSONArray arr = jsonObject.getJSONArray("symptoms");
                    JSONObject element;
                    String label = "";
                    for(int i = 0; i < arr.length(); i++){
                        element = arr.getJSONObject(i);
                        label = element.getString("label");
                        allSymptomsObjectList.add(element);
                        allSymptomsList.add(label);
//                        Timber.d(" " + allSymptomsObjectList);
                    }

                    Timber.d("+++++++++++++++++++++++++");
//                    Timber.d(" " + allSymptomsObjectList);
                    Timber.d("+++++++++++++++++++++++++");
                    Timber.d(" " + allSymptomsObjectList.get(1));
                    Timber.d(" " + label);


                    initAutoComplete();


//                    SessionManager.setRefreshToken(getApplicationContext(), jsonObject.getString("refresh"));
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
                SessionManager.setIsSignedIn(getApplicationContext(),false);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                Timber.d("Token Invalidate");
//                tokenRefresh();
//                post_response_text.setText("Post Data : Response Failed");
            }
        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("token", SessionManager.getAccessToken());
//                return params;
//            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + SessionManager.getAccessToken());
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }


        };

        requestQueue.add(stringRequest);
//        Timber.d(isValid[0]);
    }
}