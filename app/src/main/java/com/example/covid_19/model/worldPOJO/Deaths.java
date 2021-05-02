package com.example.covid_19.model.worldPOJO;

import com.google.gson.annotations.SerializedName;

public class Deaths{

	@SerializedName("new")
	private String jsonMemberNew;

	@SerializedName("total")
	private int total;

	@SerializedName("1M_pop")
	private String jsonMember1MPop;

	public void setJsonMemberNew(String jsonMemberNew){
		this.jsonMemberNew = jsonMemberNew;
	}

	public String getJsonMemberNew(){
		return jsonMemberNew;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setJsonMember1MPop(String jsonMember1MPop){
		this.jsonMember1MPop = jsonMember1MPop;
	}

	public String getJsonMember1MPop(){
		return jsonMember1MPop;
	}
}