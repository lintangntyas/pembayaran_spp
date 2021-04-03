package com.example.ukk_lintang2021.Model.Petugas;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePetugas{

	@SerializedName("result")
	private List<PetugasResultItem> result;

	@SerializedName("code")
	private String code;

	@SerializedName("message")
	private String message;

	public void setResult(List<PetugasResultItem> result){
		this.result = result;
	}

	public List<PetugasResultItem> getResult(){
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