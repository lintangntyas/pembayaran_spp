package com.example.ukk_lintang2021.Model.Siswa;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SiswaResultItem implements Serializable {

	@SerializedName("nama")
	private String nama;

	@SerializedName("nisn")
	private String nisn;

	@SerializedName("id_spp")
	private String idSpp;

	@SerializedName("nis")
	private String nis;

	@SerializedName("id_kelas")
	private String idKelas;

	@SerializedName("no_telp")
	private String noTelp;

	@SerializedName("alamat")
	private String alamat;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setNisn(String nisn){
		this.nisn = nisn;
	}

	public String getNisn(){
		return nisn;
	}

	public void setIdSpp(String idSpp){
		this.idSpp = idSpp;
	}

	public String getIdSpp(){
		return idSpp;
	}

	public void setNis(String nis){
		this.nis = nis;
	}

	public String getNis(){
		return nis;
	}

	public void setIdKelas(String idKelas){
		this.idKelas = idKelas;
	}

	public String getIdKelas(){
		return idKelas;
	}

	public void setNoTelp(String noTelp){
		this.noTelp = noTelp;
	}

	public String getNoTelp(){
		return noTelp;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}
}