package com.example.covid_19.model.worldPOJO;

import com.google.gson.annotations.SerializedName;

public class Cases{

	@SerializedName("new")
	private String jsonMemberNew;

	@SerializedName("recovered")
	private int recovered;

	@SerializedName("total")
	private int total;

	@SerializedName("critical")
	private int critical;

	@SerializedName("active")
	private int active;

	@SerializedName("1M_pop")
	private String jsonMember1MPop;

	public void setJsonMemberNew(String jsonMemberNew){
		this.jsonMemberNew = jsonMemberNew;
	}

	public String getJsonMemberNew(){
		return jsonMemberNew;
	}

	public void setRecovered(int recovered){
		this.recovered = recovered;
	}

	public int getRecovered(){
		return recovered;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setCritical(int critical){
		this.critical = critical;
	}

	public int getCritical(){
		return critical;
	}

	public void setActive(int active){
		this.active = active;
	}

	public int getActive(){
		return active;
	}

	public void setJsonMember1MPop(String jsonMember1MPop){
		this.jsonMember1MPop = jsonMember1MPop;
	}

	public String getJsonMember1MPop(){
		return jsonMember1MPop;
	}
}