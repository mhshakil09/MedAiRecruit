package com.example.medairecruit.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medairecruit.databinding.ActivityHomeBinding;
import com.example.medairecruit.ui.MainActivity;
import com.example.medairecruit.ui.profile.ProfileActivity;
import com.example.medairecruit.ui.sign_in.SignInActivity;
import com.example.medairecruit.utils.Helper;
import com.example.medairecruit.utils.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        initClickListener();
        initData();

        //TODO remove commenting the below code
        tokenVerify();
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

        binding.profileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });

        //TODO remove the following code with button
//        binding.verifyTokenBtn.setVisibility(View.GONE);
        binding.verifyTokenBtn.setOnClickListener(view -> {
            tokenVerify();
            initData();
        });

        //TODO remove the following code with button
//        binding.refreshTokenBtn.setVisibility(View.GONE);
        binding.refreshTokenBtn.setOnClickListener(view -> {
            tokenRefresh();
            initData();
        });
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
                    binding.token.setText(jsonObject.getString("access"));
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