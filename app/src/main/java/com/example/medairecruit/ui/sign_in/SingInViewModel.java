package com.example.medairecruit.ui.sign_in;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medairecruit.ui.MainActivity;
import com.example.medairecruit.utils.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class SingInViewModel extends ViewModel {

    public void postRequest(Context context, String userId, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                    SessionManager.setRefreshToken(context, jsonObject.getString("refresh"));
                    SessionManager.setAccessToken(context, jsonObject.getString("access"));
                    if (!jsonObject.getString("refresh").isEmpty() && !jsonObject.getString("access").isEmpty() ) {
                        SessionManager.setIsSignedIn(context, true);
                        context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
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
