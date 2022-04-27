package com.example.medairecruit.api.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AllSymptomsModel implements Parcelable {
    String weekDay;
    String weekDayDetails;

    public AllSymptomsModel(String weekDay, String weekDayDetails) {
        this.weekDay = weekDay;
        this.weekDayDetails = weekDayDetails;
    }

    protected AllSymptomsModel(Parcel in) {
        weekDay = in.readString();
        weekDayDetails = in.readString();
    }

    public static final Creator<com.example.medairecruit.api.model.AllSymptomsModel>
            CREATOR = new Creator<com.example.medairecruit.api.model.AllSymptomsModel>() {
        @Override
        public com.example.medairecruit.api.model.AllSymptomsModel createFromParcel(Parcel in) {
            return new com.example.medairecruit.api.model.AllSymptomsModel(in);
        }

        @Override
        public com.example.medairecruit.api.model.AllSymptomsModel[] newArray(int size) {
            return new com.example.medairecruit.api.model.AllSymptomsModel[size];
        }
    };

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getWeekDayDetails() {
        return weekDayDetails;
    }

    public void setWeekDayDetails(String weekDayDetails) {
        this.weekDayDetails = weekDayDetails;
    }

    public static Creator<com.example.medairecruit.api.model.AllSymptomsModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "DoctorWeekdayListModel{" +
                "weekDay='" + weekDay + '\'' +
                ", weekDayDetails='" + weekDayDetails + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(weekDay);
        dest.writeString(weekDayDetails);
    }
}
