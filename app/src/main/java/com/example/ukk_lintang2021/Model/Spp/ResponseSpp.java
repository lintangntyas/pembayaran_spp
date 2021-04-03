package com.example.ukk_lintang2021.Model.Spp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSpp{

	@SerializedName("result")
	private List<SppResultItem> result;

	@SerializedName("code")
	private String code;

	@SerializedName("message")
	private String message;

	public void setResult(List<SppResultItem> result){
		this.result = result;
	}

	public List<SppResultItem> getResult(){
		return result;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}