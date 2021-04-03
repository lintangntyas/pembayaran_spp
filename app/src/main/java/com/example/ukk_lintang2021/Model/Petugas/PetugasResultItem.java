package com.example.ukk_lintang2021.Model.Petugas;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PetugasResultItem implements Serializable {

	@SerializedName("password")
	private String password;

	@SerializedName("level")
	private String level;

	@SerializedName("nama_petugas")
	private String namaPetugas;

	@SerializedName("id_petugas")
	private String idPetugas;

	@SerializedName("username")
	private String username;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setLevel(String level){
		this.level = level;
	}

	public String getLevel(){
		return level;
	}

	public void setNamaPetugas(String namaPetugas){
		this.namaPetugas = namaPetugas;
	}

	public String getNamaPetugas(){
		return namaPetugas;
	}

	public void setIdPetugas(String idPetugas){
		this.idPetugas = idPetugas;
	}

	public String getIdPetugas(){
		return idPetugas;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}