package com.hayya5k.simanja.Pengaduan.All.DetailItemPengaduan.ModelDetailItemPengaduan;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ModelDetailItemPengaduan{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("meta")
	private Meta meta;

	@SerializedName("komentar")
	private List<KomentarItem> komentar;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	public void setKomentar(List<KomentarItem> komentar){
		this.komentar = komentar;
	}

	public List<KomentarItem> getKomentar(){
		return komentar;
	}

	@Override
 	public String toString(){
		return 
			"ModelDetailItemPengaduan{" + 
			"data = '" + data + '\'' + 
			",meta = '" + meta + '\'' + 
			",komentar = '" + komentar + '\'' + 
			"}";
		}
}