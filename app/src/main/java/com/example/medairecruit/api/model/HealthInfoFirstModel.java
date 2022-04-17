package com.example.medairecruit.api.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HealthInfoFirstModel implements Parcelable {
    String tobacco;
    String smoking;
    String asthma;
    String covid;

    String vaccinated;
    String highBP;
    String highBPInFamily;
    String heartDisease;
    String heartDiseaseInFamily;

    public HealthInfoFirstModel(String tobacco, String smoking, String asthma,
                                String covid, String vaccinated, String highBP,
                                String highBPInFamily, String heartDisease,
                                String heartDiseaseInFamily) {
        this.tobacco = tobacco;
        this.smoking = smoking;
        this.asthma = asthma;
        this.covid = covid;

        this.vaccinated = vaccinated;
        this.highBP = highBP;
        this.highBPInFamily = highBPInFamily;
        this.heartDisease = heartDisease;
        this.heartDiseaseInFamily = heartDiseaseInFamily;
    }

    protected HealthInfoFirstModel(Parcel in) {
        tobacco = in.readString();
        smoking = in.readString();
        asthma = in.readString();
        covid = in.readString();

        vaccinated = in.readString();
        highBP = in.readString();
        highBPInFamily = in.readString();
        heartDisease = in.readString();
        heartDiseaseInFamily = in.readString();
    }

    public static final Creator<HealthInfoFirstModel> CREATOR = new Creator<HealthInfoFirstModel>() {
        @Override
        public HealthInfoFirstModel createFromParcel(Parcel in) {
            return new HealthInfoFirstModel(in);
        }

        @Override
        public HealthInfoFirstModel[] newArray(int size) {
            return new HealthInfoFirstModel[size];
        }
    };

    public static Creator<HealthInfoFirstModel> getCREATOR() {
        return CREATOR;
    }

    public HealthInfoFirstModel() {

    }

    @Override
    public String toString() {
        return "HealthInfoFirstModel{" +
                "tobacco='" + tobacco + '\'' +
                ", smoking='" + smoking + '\'' +
                ", asthma='" + asthma + '\'' +
                ", covid='" + covid + '\'' +
                ", vaccinated='" + vaccinated + '\'' +
                ", highBP='" + highBP + '\'' +
                ", highBPInFamily='" + highBPInFamily + '\'' +
                ", heartDisease='" + heartDisease + '\'' +
                ", heartDiseaseInFamily='" + heartDiseaseInFamily + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tobacco);
        dest.writeString(smoking);
        dest.writeString(asthma);
        dest.writeString(covid);

        dest.writeString(vaccinated);
        dest.writeString(highBP);
        dest.writeString(highBPInFamily);
        dest.writeString(heartDisease);
        dest.writeString(heartDiseaseInFamily);

    }
}
