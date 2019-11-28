package com.hayya5k.simanja.Helper.ModelMeta;

import com.google.gson.annotations.SerializedName;


public class ModelMeta{

	@SerializedName("meta")
	private Meta meta;

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	@Override
 	public String toString(){
		return 
			"ModelMeta{" + 
			"meta = '" + meta + '\'' + 
			"}";
		}
}