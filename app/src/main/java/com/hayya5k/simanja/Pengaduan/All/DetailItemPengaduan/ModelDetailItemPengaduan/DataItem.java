package com.hayya5k.simanja.Pengaduan.All.DetailItemPengaduan.ModelDetailItemPengaduan;

import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("tgl_input")
	private String tglInput;

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("foto")
	private String foto;

	@SerializedName("lng")
	private String lng;

	@SerializedName("id_pengaduan")
	private String idPengaduan;

	@SerializedName("judul")
	private String judul;

	@SerializedName("lat")
	private String lat;

	@SerializedName("alamat")
	private String alamat;

	public void setTglInput(String tglInput){
		this.tglInput = tglInput;
	}

	public String getTglInput(){
		return tglInput;
	}

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setIdPengaduan(String idPengaduan){
		this.idPengaduan = idPengaduan;
	}

	public String getIdPengaduan(){
		return idPengaduan;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"tgl_input = '" + tglInput + '\'' + 
			",pesan = '" + pesan + '\'' + 
			",foto = '" + foto + '\'' + 
			",lng = '" + lng + '\'' + 
			",id_pengaduan = '" + idPengaduan + '\'' + 
			",judul = '" + judul + '\'' + 
			",lat = '" + lat + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}
}