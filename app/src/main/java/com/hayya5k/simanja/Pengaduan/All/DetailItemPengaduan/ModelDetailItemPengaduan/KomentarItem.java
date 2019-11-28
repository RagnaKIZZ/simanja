package com.hayya5k.simanja.Pengaduan.All.DetailItemPengaduan.ModelDetailItemPengaduan;

import com.google.gson.annotations.SerializedName;


public class KomentarItem{

	@SerializedName("komentar")
	private String komentar;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public void setKomentar(String komentar){
		this.komentar = komentar;
	}

	public String getKomentar(){
		return komentar;
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

	@Override
 	public String toString(){
		return 
			"KomentarItem{" + 
			"komentar = '" + komentar + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}