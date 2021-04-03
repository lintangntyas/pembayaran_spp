package com.example.ukk_lintang2021.Model.Register;

import com.google.gson.annotations.SerializedName;

public class RegisterData {

	@SerializedName("role")
	private String role;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}