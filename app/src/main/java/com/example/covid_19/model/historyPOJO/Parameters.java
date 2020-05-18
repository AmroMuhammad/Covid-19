
package com.example.covid_19.model.historyPOJO;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Parameters {

    @SerializedName("country")
    private String mCountry;

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

}
