package com.hayya5k.simanja.Register.ModelJabatan;

import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("id_jabatan")
	private String idJabatan;

	@SerializedName("nama_jabatan")
	private String namaJabatan;

	public void setIdJabatan(String idJabatan){
		this.idJabatan = idJabatan;
	}

	public String getIdJabatan(){
		return idJabatan;
	}

	public void setNamaJabatan(String namaJabatan){
		this.namaJabatan = namaJabatan;
	}

	public String getNamaJabatan(){
		return namaJabatan;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"id_jabatan = '" + idJabatan + '\'' + 
			",nama_jabatan = '" + namaJabatan + '\'' + 
			"}";
		}
}