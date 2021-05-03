package com.example.covid_19.model.worldPOJO;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class APIResponse{

	@SerializedName("response")
	private List<ResponseItem> response;

	@SerializedName("get")
	private String get;

	@SerializedName("parameters")
	private Parameters parameters;

	@SerializedName("results")
	private int results;

	@SerializedName("errors")
	private List<Object> errors;

	public void setResponse(List<ResponseItem> response){
		this.response = response;
	}

	public List<ResponseItem> getResponse(){
		return response;
	}

	public void setGet(String get){
		this.get = get;
	}

	public String getGet(){
		return get;
	}

	public void setParameters(Parameters parameters){
		this.parameters = parameters;
	}

	public Parameters getParameters(){
		return parameters;
	}

	public void setResults(int results){
		this.results = results;
	}

	public int getResults(){
		return results;
	}

	public void setErrors(List<Object> errors){
		this.errors = errors;
	}

	public List<Object> getErrors(){
		return errors;
	}
}