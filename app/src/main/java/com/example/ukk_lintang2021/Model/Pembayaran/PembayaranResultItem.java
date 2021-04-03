package com.example.ukk_lintang2021.Model.Pembayaran;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PembayaranResultItem implements Serializable {

	@SerializedName("bulan_dibayar")
	private String bulanDibayar;

	@SerializedName("id_pembayaran")
	private String idPembayaran;

	@SerializedName("nisn")
	private String nisn;

	@SerializedName("tahun_dibayar")
	private String tahunDibayar;

	@SerializedName("id_spp")
	private String idSpp;

	@SerializedName("id_petugas")
	private String idPetugas;

	@SerializedName("jumlah_bayar")
	private String jumlahBayar;

	@SerializedName("tgl_bayar")
	private String tglBayar;

	public void setBulanDibayar(String bulanDibayar){
		this.bulanDibayar = bulanDibayar;
	}

	public String getBulanDibayar(){
		return bulanDibayar;
	}

	public void setIdPembayaran(String idPembayaran){
		this.idPembayaran = idPembayaran;
	}

	public String getIdPembayaran(){
		return idPembayaran;
	}

	public void setNisn(String nisn){
		this.nisn = nisn;
	}

	public String getNisn(){
		return nisn;
	}

	public void setTahunDibayar(String tahunDibayar){
		this.tahunDibayar = tahunDibayar;
	}

	public String getTahunDibayar(){
		return tahunDibayar;
	}

	public void setIdSpp(String idSpp){
		this.idSpp = idSpp;
	}

	public String getIdSpp(){
		return idSpp;
	}

	public void setIdPetugas(String idPetugas){
		this.idPetugas = idPetugas;
	}

	public String getIdPetugas(){
		return idPetugas;
	}

	public void setJumlahBayar(String jumlahBayar){
		this.jumlahBayar = jumlahBayar;
	}

	public String getJumlahBayar(){
		return jumlahBayar;
	}

	public void setTglBayar(String tglBayar){
		this.tglBayar = tglBayar;
	}

	public String getTglBayar(){
		return tglBayar;
	}
}