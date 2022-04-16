package com.example.medairecruit.api;

import android.content.Context;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medairecruit.ui.MainActivity;
import com.example.medairecruit.utils.Helper;
import com.example.medairecruit.utils.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class TokenApi {


    public void tokenVerify(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url="http://54.179.12.36/api/v1/accounts/token/verify";

        Timber.d(SessionManager.getAccessToken());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Timber.d("------------------------------");
                    Timber.d(jsonObject.toString());
                    Timber.d("------------------------------");
                    Timber.d(jsonObject.getString("details"));
                    Timber.d(jsonObject.getString("code"));

                    Helper.toast(context, "token is valid");

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

    public void tokenRefresh(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url="http://54.179.12.36/api/v1/accounts/mobile/refresh";

        Timber.d(SessionManager.getRefreshToken());
        Timber.d(SessionManager.getAccessToken());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Timber.d("------------------------------");
                    Timber.d(jsonObject.toString());
                    Timber.d("------------------------------");
                    Timber.d(jsonObject.getString("refresh"));
                    Timber.d(jsonObject.getString("access"));

                    Timber.d("Token changed");

                    SessionManager.setRefreshToken(context, jsonObject.getString("refresh"));
                    SessionManager.setAccessToken(context, jsonObject.getString("access"));

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
//                post_response_text.setText("Post Data : Response Failed");
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

//    public void postRequest(Context context, String userId, String password) {
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        String url="http://54.179.12.36/api/v1/accounts/mobile/login";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //let's parse json data
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    Timber.d("------------------------------");
//                    Timber.d(jsonObject.toString());
//                    Timber.d("------------------------------");
//                    Timber.d(jsonObject.getString("details"));
//                    Timber.d(jsonObject.getString("code"));
//
////                    SessionManager.setRefreshToken(context, jsonObject.getString("refresh"));
////                    SessionManager.setAccessToken(context, jsonObject.getString("access"));
//
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                    Timber.d("POST DATA : unable to Parse Json");
////                    post_response_text.setText("POST DATA : unable to Parse Json");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Timber.d("Post Data : Response Failed");
//                Timber.d(error);
////                post_response_text.setText("Post Data : Response Failed");
//            }
//        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("token", SessionManager.getAccessToken());
//                return params;
//            }
//
//            @Override
//            public Map<String,String> getHeaders() throws AuthFailureError {
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//
//        requestQueue.add(stringRequest);
//
//    }
}
