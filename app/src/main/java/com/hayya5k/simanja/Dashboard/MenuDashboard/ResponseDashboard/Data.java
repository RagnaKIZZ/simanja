package com.hayya5k.simanja.Dashboard.MenuDashboard.ResponseDashboard;

import com.google.gson.annotations.SerializedName;


public class Data{

	@SerializedName("pengaduan_total")
	private String pengaduanTotal;

	@SerializedName("pengaduan_selesai")
	private String pengaduanSelesai;

	@SerializedName("pengaduan_proses")
	private String pengaduanProses;

	public void setPengaduanTotal(String pengaduanTotal){
		this.pengaduanTotal = pengaduanTotal;
	}

	public String getPengaduanTotal(){
		return pengaduanTotal;
	}

	public void setPengaduanSelesai(String pengaduanSelesai){
		this.pengaduanSelesai = pengaduanSelesai;
	}

	public String getPengaduanSelesai(){
		return pengaduanSelesai;
	}

	public void setPengaduanProses(String pengaduanProses){
		this.pengaduanProses = pengaduanProses;
	}

	public String getPengaduanProses(){
		return pengaduanProses;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"pengaduan_total = '" + pengaduanTotal + '\'' + 
			",pengaduan_selesai = '" + pengaduanSelesai + '\'' + 
			",pengaduan_proses = '" + pengaduanProses + '\'' + 
			"}";
		}
}