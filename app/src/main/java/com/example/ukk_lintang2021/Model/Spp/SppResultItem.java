package com.example.ukk_lintang2021.Model.Spp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SppResultItem implements Serializable {

	@SerializedName("tahun")
	private String tahun;

	@SerializedName("nominal")
	private String nominal;

	@SerializedName("id_spp")
	private String idSpp;

	public void setTahun(String tahun){
		this.tahun = tahun;
	}

	public String getTahun(){
		return tahun;
	}

	public void setNominal(String nominal){
		this.nominal = nominal;
	}

	public String getNominal(){
		return nominal;
	}

	public void setIdSpp(String idSpp){
		this.idSpp = idSpp;
	}

	public String getIdSpp(){
		return idSpp;
	}

}