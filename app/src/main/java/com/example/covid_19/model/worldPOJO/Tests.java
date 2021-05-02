package com.example.covid_19.model.worldPOJO;

import com.google.gson.annotations.SerializedName;

public class Tests{

	@SerializedName("total")
	private Object total;

	@SerializedName("1M_pop")
	private Object jsonMember1MPop;

	public void setTotal(Object total){
		this.total = total;
	}

	public Object getTotal(){
		return total;
	}

	public void setJsonMember1MPop(Object jsonMember1MPop){
		this.jsonMember1MPop = jsonMember1MPop;
	}

	public Object getJsonMember1MPop(){
		return jsonMember1MPop;
	}
}