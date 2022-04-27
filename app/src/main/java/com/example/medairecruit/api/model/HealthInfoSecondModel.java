package com.example.medairecruit.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class HealthInfoSecondModel extends JSONObject implements Parcelable {
    String patient_id;

    String tobacco;
    String smoking;
    String asthma;
    String covid;

    String vaccinated;
    String highBP;
    String highBPInFamily;
    String heartDisease;
    String heartDiseaseInFamily;


    String allergy;
    String regular_med;
    String menopausal_status;

    String diabetes;
    String diabetes_his;
    String pregnancy;
    String recreational_drug;

    public HealthInfoSecondModel(
            String patient_id,
            String tobacco, String smoking, String asthma,
            String covid, String vaccinated, String highBP,
            String highBPInFamily, String heartDisease,
            String heartDiseaseInFamily,

            String allergy, String regular_med, String menopausal_status,
            String diabetes, String diabetes_his,
            String pregnancy, String recreational_drug) {

        this.patient_id = patient_id;

        this.tobacco = tobacco;
        this.smoking = smoking;
        this.asthma = asthma;
        this.covid = covid;

        this.vaccinated = vaccinated;
        this.highBP = highBP;
        this.highBPInFamily = highBPInFamily;
        this.heartDisease = heartDisease;
        this.heartDiseaseInFamily = heartDiseaseInFamily;


        this.allergy = allergy;
        this.regular_med = regular_med;
        this.menopausal_status = menopausal_status;

        this.diabetes = diabetes;
        this.diabetes_his = diabetes_his;
        this.pregnancy = pregnancy;
        this.recreational_drug = recreational_drug;
    }

    protected HealthInfoSecondModel(Parcel in) {

        patient_id = in.readString();

        tobacco = in.readString();
        smoking = in.readString();
        asthma = in.readString();
        covid = in.readString();

        vaccinated = in.readString();
        highBP = in.readString();
        highBPInFamily = in.readString();
        heartDisease = in.readString();
        heartDiseaseInFamily = in.readString();


        allergy = in.readString();
        regular_med = in.readString();
        menopausal_status = in.readString();

        diabetes = in.readString();
        diabetes_his = in.readString();
        pregnancy = in.readString();
        recreational_drug = in.readString();
    }

    public static final Creator<HealthInfoSecondModel> CREATOR = new Creator<HealthInfoSecondModel>() {
        @Override
        public HealthInfoSecondModel createFromParcel(Parcel in) {
            return new HealthInfoSecondModel(in);
        }

        @Override
        public HealthInfoSecondModel[] newArray(int size) {
            return new HealthInfoSecondModel[size];
        }
    };

    public static Creator<HealthInfoSecondModel> getCREATOR() {
        return CREATOR;
    }

    public HealthInfoSecondModel() {

    }

    @Override
    public String toString() {
        return "HealthInfoSecondModel{" +
                "patient_id='" + patient_id + '\'' +
                ", allergy='" + allergy + '\'' +
                ", regular_med='" + regular_med + '\'' +
                ", menopausal_status='" + menopausal_status + '\'' +

                ", diabetes='" + diabetes + '\'' +
                ", diabetes_his='" + diabetes_his + '\'' +
                ", pregnancy='" + pregnancy + '\'' +
                ", recreational_drug='" + recreational_drug + '\'' +
                '}';
    }

    public String getPatientId() {
        return patient_id;
    }
    public String getTobacco() {
        return tobacco;
    }
    public String getSmoking() {
        return smoking;
    }
    public String getAsthma() {
        return asthma;
    }
    public String getCovid() {
        return covid;
    }
    public String getVaccinated() {
        return vaccinated;
    }
    public String getHighBP() {
        return highBP;
    }
    public String getHighBPInFamily() {
        return highBPInFamily;
    }
    public String getHeartDisease() {
        return heartDisease;
    }
    public String getHeartDiseaseInFamily() {
        return heartDiseaseInFamily;
    }


    public String getAllergy() {
        return allergy;
    }
    public String getRegularMed() {
        return regular_med;
    }
    public String getMenopausalStatus() {
        return menopausal_status;
    }

    public String getDiabetes() {
        return diabetes;
    }
    public String getDiabetesHis() {
        return diabetes_his;
    }
    public String getPregnancy() {
        return pregnancy;
    }
    public String getRecreationalDrug() {
        return recreational_drug;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(patient_id);

        dest.writeString(tobacco);
        dest.writeString(smoking);
        dest.writeString(asthma);
        dest.writeString(covid);

        dest.writeString(vaccinated);
        dest.writeString(highBP);
        dest.writeString(highBPInFamily);
        dest.writeString(heartDisease);
        dest.writeString(heartDiseaseInFamily);


        dest.writeString(allergy);
        dest.writeString(regular_med);
        dest.writeString(menopausal_status);

        dest.writeString(diabetes);
        dest.writeString(diabetes_his);
        dest.writeString(pregnancy);
        dest.writeString(recreational_drug);

    }
}
