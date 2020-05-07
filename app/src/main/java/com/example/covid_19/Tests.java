
package com.example.covid_19;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Tests {

    @SerializedName("total")
    private Object mTotal;

    public Object getTotal() {
        return mTotal;
    }

    public void setTotal(Object total) {
        mTotal = total;
    }

}
