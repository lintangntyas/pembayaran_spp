package com.example.ukk_lintang2021.Model.Pembayaran;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePembayaran{

	@SerializedName("result")
	private List<PembayaranResultItem> result;

	@SerializedName("code")
	private String code;

	@SerializedName("message")
	private String message;

	public void setResult(List<PembayaranResultItem> result){
		this.result = result;
	}

	public List<PembayaranResultItem> getResult(){
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