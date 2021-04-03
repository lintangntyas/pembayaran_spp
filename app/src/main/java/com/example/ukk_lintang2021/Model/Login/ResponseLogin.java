package com.example.ukk_lintang2021.Model.Login;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin{

	@SerializedName("loginData")
	private LoginData loginData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setLoginData(LoginData loginData){
		this.loginData = loginData;
	}

	public LoginData getLoginData(){
		return loginData;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}