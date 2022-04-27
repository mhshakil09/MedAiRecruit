package com.example.medairecruit.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class PatientRegistrationProfileModel extends JSONObject implements Parcelable {
    String phone;
    String first_name;
    String last_name;
    String gender;
    Integer age;
    String marital_status;

//    String religion;
    String education;
    String occupation;
    String ethnicity;
    String country;
    String region;

    //data not provided, so default would be empty
    String address_line;
    String zip_post_code;
    String city_vill;
//    String DOB;
    String blood_group;
    Integer monthly_expenditure;

    String person_location;
    String person_type;
    String consent_given;
    String consent_image;

    public PatientRegistrationProfileModel(
            String phone,
            String first_name,
            String last_name,
            String gender,
            Integer age,
            String marital_status,

//            String religion,
            String education,
            String occupation,
            String ethnicity,
            String country,
            String region,

            //data not provided, so default would be empty
            String address_line,
            String zip_post_code,
            String city_vill,
//            String DOB,
            String blood_group,
            Integer monthly_expenditure,

            String person_location,
            String person_type,
            String consent_given,
            String consent_image
    ) {
        this.phone = phone;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.age = age;
        this.marital_status = marital_status;

//        this.religion = religion;
        this.education = education;
        this.occupation = occupation;
        this.ethnicity = ethnicity;
        this.country = country;
        this.region = region;


        this.address_line = address_line;
        this.zip_post_code = zip_post_code;
        this.city_vill = city_vill;
//        this.DOB = DOB;
        this.blood_group = blood_group;
        this.monthly_expenditure = monthly_expenditure;

        this.person_location = person_location;
        this.person_type = person_type;
        this.consent_given = consent_given;
        this.consent_image = consent_image;
    }

    protected PatientRegistrationProfileModel(Parcel in) {
        phone = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        gender = in.readString();
        age = in.readInt();
        marital_status = in.readString();

//        religion = in.readString();
        education = in.readString();
        occupation = in.readString();
        ethnicity = in.readString();
        country = in.readString();
        region = in.readString();


        address_line = in.readString();
        zip_post_code = in.readString();
        city_vill = in.readString();
//        DOB = in.readString();
        blood_group = in.readString();
        monthly_expenditure = in.readInt();

        person_location = in.readString();
        person_type = in.readString();
        consent_given = in.readString();
        consent_image = in.readString();
    }

    public static final Creator<PatientRegistrationProfileModel> CREATOR = new Creator<PatientRegistrationProfileModel>() {
        @Override
        public PatientRegistrationProfileModel createFromParcel(Parcel in) {
            return new PatientRegistrationProfileModel(in);
        }

        @Override
        public PatientRegistrationProfileModel[] newArray(int size) {
            return new PatientRegistrationProfileModel[size];
        }
    };

    public static Creator<PatientRegistrationProfileModel> getCREATOR() {
        return CREATOR;
    }

    public PatientRegistrationProfileModel() {

    }

    @Override
    public String toString() {

        return "PatientRegistrationProfileModel{" +
                "phone='" + phone + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age + ' ' +
                ", marital_status='" + marital_status + '\'' +

//                ", religion='" + religion + '\'' +
                ", education='" + education + '\'' +
                ", occupation='" + occupation + '\'' +
                ", ethnicity='" + ethnicity + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +


                ", address_line='" + address_line + '\'' +
                ", zip_post_code='" + zip_post_code + '\'' +
                ", city_vill='" + city_vill + '\'' +
//                ", DOB='" + DOB + '\'' +
                ", blood_group='" + blood_group + '\'' +
                ", monthly_expenditure=" + monthly_expenditure + ' ' +

                ", person_location='" + person_location + '\'' +
                ", person_type='" + person_type + '\'' +
                ", consent_given='" + consent_given + '\'' +
                ", consent_image='" + consent_image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phone);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(gender);
        dest.writeInt(age);
        dest.writeString(marital_status);

//        dest.writeString(religion);
        dest.writeString(education);
        dest.writeString(occupation);
        dest.writeString(ethnicity);
        dest.writeString(country);
        dest.writeString(region);


        dest.writeString(address_line);
        dest.writeString(zip_post_code);
        dest.writeString(city_vill);
//        dest.writeString(DOB);
        dest.writeString(blood_group);
        dest.writeInt(monthly_expenditure);

        dest.writeString(person_location);
        dest.writeString(person_type);
        dest.writeString(consent_given);
        dest.writeString(consent_image);

    }
}
