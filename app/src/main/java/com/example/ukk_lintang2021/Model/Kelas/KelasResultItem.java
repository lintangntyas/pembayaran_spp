package com.example.ukk_lintang2021.Model.Kelas;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KelasResultItem implements Serializable {

	@SerializedName("nama_kelas")
	private String namaKelas;

	@SerializedName("id_kelas")
	private String idKelas;

	@SerializedName("kompetensi_keahlian")
	private String kompetensiKeahlian;

	public void setNamaKelas(String namaKelas){
		this.namaKelas = namaKelas;
	}

	public String getNamaKelas(){
		return namaKelas;
	}

	public void setIdKelas(String idKelas){
		this.idKelas = idKelas;
	}

	public String getIdKelas(){
		return idKelas;
	}

	public void setKompetensiKeahlian(String kompetensiKeahlian){
		this.kompetensiKeahlian = kompetensiKeahlian;
	}

	public String getKompetensiKeahlian(){
		return kompetensiKeahlian;
	}
}