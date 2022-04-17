package com.example.medairecruit.ui.sign_in;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medairecruit.databinding.ActivitySignInBinding;
import com.example.medairecruit.ui.MainActivity;
import com.example.medairecruit.ui.home.HomeActivity;
import com.example.medairecruit.utils.Helper;
import com.example.medairecruit.utils.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";
//    SingInViewModel viewModel = new ViewModelProvider(this).get(SingInViewModel.class);

    String userId = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        initClickListener();
        initSignInData();
    }

    private void initSignInData() {
        binding.userId.setText("user_1234567890");
        binding.password.setText("test-1234");
    }

    private void initClickListener() {
        binding.toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Helper.toast(getApplicationContext(), "True");
                } else {
                    // The toggle is disabled
                    Helper.toast(getApplicationContext(), "False");
                }
            }
        });

        binding.forgotPasswordTV.setOnClickListener(view -> {
            Helper.toast(this, "work in progress");
        });

        binding.signInBtn.setOnClickListener(view -> {
            userId = "";
            password = "";

            if (validity()) {
                Timber.d("userid: "+userId);
                Timber.d("password: "+password);
                signInRequest();
            }
//            Helper.toast(this, "work in progress");
        });
    }

    private void signInRequest() {
        postRequest(userId, password);
    }

    private boolean validity() {
        userId = binding.userId.getText().toString().trim();
        if (userId.isEmpty()) {
            Helper.toast(this, "Fill up userId");
            return false;
        }

        password = binding.password.getText().toString().trim();
        if (password.isEmpty()) {
            Helper.toast(this, "Fill up password");
            return false;
        }
        return true;
    }

    public void postRequest(String userId, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url="http://54.179.12.36/api/v1/accounts/mobile/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    post_response_text.setText("Data 1 : " + jsonObject.getString("data_1_post")+"\n");
//                    post_response_text.append("Data 2 : " + jsonObject.getString("data_2_post")+"\n");
//                    post_response_text.append("Data 3 : " + jsonObject.getString("data_3_post")+"\n");
//                    post_response_text.append("Data 4 : " + jsonObject.getString("data_4_post")+"\n");
                    Timber.d("------------------------------");
                    Timber.d(jsonObject.toString());
                    Timber.d("------------------------------");
                    Timber.d(jsonObject.getString("refresh"));
                    Timber.d(jsonObject.getString("access"));
                    SessionManager.setRefreshToken(getApplicationContext(), jsonObject.getString("refresh"));
                    SessionManager.setAccessToken(getApplicationContext(), jsonObject.getString("access"));
                    if (!jsonObject.getString("refresh").isEmpty() && !jsonObject.getString("access").isEmpty() ) {
                        SessionManager.setIsSignedIn(getApplicationContext(), true);
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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
//                post_response_text.setText("Post Data : Response Failed");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("username", userId);
                params.put("password", password);
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
}