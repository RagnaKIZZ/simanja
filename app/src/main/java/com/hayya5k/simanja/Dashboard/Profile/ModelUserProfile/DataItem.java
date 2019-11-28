package com.hayya5k.simanja.Dashboard.Profile.ModelUserProfile;

import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("id_user_mobile")
	private String idUserMobile;

	@SerializedName("no_hp")
	private String noHp;

	@SerializedName("jabatan")
	private String jabatan;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("trash")
	private String trash;

	@SerializedName("tgl_update")
	private String tglUpdate;

	@SerializedName("tgl_input")
	private String tglInput;

	@SerializedName("nik")
	private String nik;

	@SerializedName("password")
	private String password;

	@SerializedName("nama")
	private String nama;

	@SerializedName("foto")
	private String foto;

	@SerializedName("hak_akses")
	private String hakAkses;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	@SerializedName("status")
	private String status;

	public void setIdUserMobile(String idUserMobile){
		this.idUserMobile = idUserMobile;
	}

	public String getIdUserMobile(){
		return idUserMobile;
	}

	public void setNoHp(String noHp){
		this.noHp = noHp;
	}

	public String getNoHp(){
		return noHp;
	}

	public void setJabatan(String jabatan){
		this.jabatan = jabatan;
	}

	public String getJabatan(){
		return jabatan;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setTrash(String trash){
		this.trash = trash;
	}

	public String getTrash(){
		return trash;
	}

	public void setTglUpdate(String tglUpdate){
		this.tglUpdate = tglUpdate;
	}

	public String getTglUpdate(){
		return tglUpdate;
	}

	public void setTglInput(String tglInput){
		this.tglInput = tglInput;
	}

	public String getTglInput(){
		return tglInput;
	}

	public void setNik(String nik){
		this.nik = nik;
	}

	public String getNik(){
		return nik;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setHakAkses(String hakAkses){
		this.hakAkses = hakAkses;
	}

	public String getHakAkses(){
		return hakAkses;
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

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"id_user_mobile = '" + idUserMobile + '\'' + 
			",no_hp = '" + noHp + '\'' + 
			",jabatan = '" + jabatan + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",trash = '" + trash + '\'' + 
			",tgl_update = '" + tglUpdate + '\'' + 
			",tgl_input = '" + tglInput + '\'' + 
			",nik = '" + nik + '\'' + 
			",password = '" + password + '\'' + 
			",nama = '" + nama + '\'' + 
			",foto = '" + foto + '\'' + 
			",hak_akses = '" + hakAkses + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}