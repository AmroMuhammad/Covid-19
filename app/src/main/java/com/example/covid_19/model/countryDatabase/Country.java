package com.example.covid_19.model.countryDatabase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Country")
public class Country implements Parcelable {

    public Country(@NonNull String countryName, String date, String time, String newCases, String activeCases, String criticalCases, String recoveredCases, String totalCases,
                   String newDeaths, String totalDeaths, String imageURL) {
        this.countryName = countryName;
        this.date = date;
        this.time = time;
        this.newCases = newCases;
        this.activeCases = activeCases;
        this.criticalCases = criticalCases;
        this.recoveredCases = recoveredCases;
        this.totalCases = totalCases;
        this.newDeaths = newDeaths;
        this.totalDeaths = totalDeaths;
        this.imageURL = imageURL;
    }

    @NonNull
    @PrimaryKey
    private String countryName;

    private String date;

    private String time;

    private String newCases;

    private String activeCases;

    private String criticalCases;

    private String recoveredCases;

    private String totalCases;

    private String newDeaths;

    private String totalDeaths;

    private String imageURL;



    @NonNull
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(@NonNull String countryName) {
        this.countryName = countryName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNewCases() {
        return newCases;
    }

    public void setNewCases(String newCases) {
        this.newCases = newCases;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(String activeCases) {
        this.activeCases = activeCases;
    }

    public String getCriticalCases() {
        return criticalCases;
    }

    public void setCriticalCases(String criticalCases) {
        this.criticalCases = criticalCases;
    }

    public String getRecoveredCases() {
        return recoveredCases;
    }

    public void setRecoveredCases(String recoveredCases) {
        this.recoveredCases = recoveredCases;
    }

    public String getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(String totalCases) {
        this.totalCases = totalCases;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        this.newDeaths = newDeaths;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.countryName);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeString(this.newCases);
        dest.writeString(this.activeCases);
        dest.writeString(this.criticalCases);
        dest.writeString(this.recoveredCases);
        dest.writeString(this.totalCases);
        dest.writeString(this.newDeaths);
        dest.writeString(this.totalDeaths);
    }

    protected Country(Parcel in) {
        this.countryName = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.newCases = in.readString();
        this.activeCases = in.readString();
        this.criticalCases = in.readString();
        this.recoveredCases = in.readString();
        this.totalCases = in.readString();
        this.newDeaths = in.readString();
        this.totalDeaths = in.readString();
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel source) {
            return new Country(source);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
