package com.example.covid_19.model.worldPOJO;

import com.google.gson.annotations.SerializedName;

public class Parameters{

	@SerializedName("country")
	private String country;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}
}