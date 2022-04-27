package com.example.medairecruit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    public static final String SHARED_PREFS = "shared_prefs";
    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    //----------------------------------------------------------------------------------------------
    public static void setIsSignedIn(Context context, Boolean isSignedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_signed_in", isSignedIn);
        editor.apply();
    }

    public static Boolean getIsSignedIn() {
        return sharedPreferences.getBoolean("is_signed_in", false);
    }

    public static void setUserId(Context context, String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", userId);
        editor.apply();
    }

    public static String getUserId() {
        return sharedPreferences.getString("user_id", "");
    }

    public static void setIsProfileComplete(Context context, Boolean isProfileComplete) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_profile_complete", isProfileComplete);
        editor.apply();
    }


    public static void setPatientId(Context context, String patientId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("patient_id", patientId);
        editor.apply();
    }

    public static String getPatientId() {
        return sharedPreferences.getString("patient_id", "");
    }



    public static Boolean getIsProfileComplete() {
        return sharedPreferences.getBoolean("is_profile_complete", false);
    }


    public static void setAccessToken(Context context, String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token", accessToken);
        editor.apply();
    }

    public static String getAccessToken() {
        return sharedPreferences.getString("access_token", "");
    }

    public static void setRefreshToken(Context context, String refreshToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("refresh_token", refreshToken);
        editor.apply();
    }

    public static String getRefreshToken() {
        return sharedPreferences.getString("refresh_token", "");
    }
    //----------------------------------------------------------------------------------------------


    public static void setSelectedTimeSlot(Context context, String timeSlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selected_time_slot", timeSlot);
        editor.apply();
    }

    public static String getSelectedTimeSlot() {
        return sharedPreferences.getString("selected_time_slot", "");
    }

    public static void setSelectedWeekDaySlot(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selected_week_day_slot", weekDaySlot);
        editor.apply();
    }

    public static String getSelectedWeekDaySlot() {
        return sharedPreferences.getString("selected_week_day_slot", "");
    }

    public static void setSelectedWeekDayDetailsSlot(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selected_week_day_details_slot", weekDaySlot);
        editor.apply();
    }

    public static String getSelectedWeekDayDetailsSlot() {
        return sharedPreferences.getString("selected_week_day_details_slot", "");
    }

    // for Doctor's appoint schedule
    // weekdays
    public static void setSaturday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_saturday", weekDaySlot);
        editor.apply();
    }

    public static String getSaturday() {
        return sharedPreferences.getString("appointment_on_saturday", "n");
    }

    public static void setSunday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_sunday", weekDaySlot);
        editor.apply();
    }

    public static String getSunday() {
        return sharedPreferences.getString("appointment_on_sunday", "n");
    }

    public static void setMonday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_monday", weekDaySlot);
        editor.apply();
    }

    public static String getMonday() {
        return sharedPreferences.getString("appointment_on_monday", "n");
    }

    public static void setTuesday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_tuesday", weekDaySlot);
        editor.apply();
    }

    public static String getTuesday() {
        return sharedPreferences.getString("appointment_on_tuesday", "n");
    }

    public static void setWednesday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_wednesday", weekDaySlot);
        editor.apply();
    }

    public static String getWednesday() {
        return sharedPreferences.getString("appointment_on_wednesday", "n");
    }

    public static void setThursday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_thursday", weekDaySlot);
        editor.apply();
    }

    public static String getThursday() {
        return sharedPreferences.getString("appointment_on_thursday", "n");
    }

    public static void setFriday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_friday", weekDaySlot);
        editor.apply();
    }

    public static String getFriday() {
        return sharedPreferences.getString("appointment_on_friday", "n");
    }








}
