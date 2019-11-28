package com.hayya5k.simanja.Pengaduan.ModelDetailPengaduan;

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

	public String getTglInput() {
		return tglInput;
	}

	public DataItem setTglInput(String tglInput) {
		this.tglInput = tglInput;
		return this;
	}

	public String getPesan() {
		return pesan;
	}

	public DataItem setPesan(String pesan) {
		this.pesan = pesan;
		return this;
	}

	public String getFoto() {
		return foto;
	}

	public DataItem setFoto(String foto) {
		this.foto = foto;
		return this;
	}

	public String getLng() {
		return lng;
	}

	public DataItem setLng(String lng) {
		this.lng = lng;
		return this;
	}

	public String getIdPengaduan() {
		return idPengaduan;
	}

	public DataItem setIdPengaduan(String idPengaduan) {
		this.idPengaduan = idPengaduan;
		return this;
	}

	public String getJudul() {
		return judul;
	}

	public DataItem setJudul(String judul) {
		this.judul = judul;
		return this;
	}

	public String getLat() {
		return lat;
	}

	public DataItem setLat(String lat) {
		this.lat = lat;
		return this;
	}

	public String getAlamat() {
		return alamat;
	}

	public DataItem setAlamat(String alamat) {
		this.alamat = alamat;
		return this;
	}

	@Override
	public String toString() {
		return "DataItem{" +
				"tglInput='" + tglInput + '\'' +
				", pesan='" + pesan + '\'' +
				", foto='" + foto + '\'' +
				", lng='" + lng + '\'' +
				", idPengaduan='" + idPengaduan + '\'' +
				", judul='" + judul + '\'' +
				", lat='" + lat + '\'' +
				", alamat='" + alamat + '\'' +
				'}';
	}
}