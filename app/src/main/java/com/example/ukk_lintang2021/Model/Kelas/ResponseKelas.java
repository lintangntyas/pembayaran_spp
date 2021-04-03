package com.example.ukk_lintang2021.Model.Kelas;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKelas{

	@SerializedName("result")
	private List<KelasResultItem> result;

	@SerializedName("code")
	private String code;

	@SerializedName("message")
	private String message;

	public void setResult(List<KelasResultItem> result){
		this.result = result;
	}

	public List<KelasResultItem> getResult(){
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