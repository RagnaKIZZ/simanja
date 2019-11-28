package com.hayya5k.simanja.Login.ModelLogin;

import com.google.gson.annotations.SerializedName;


public class ModelLogin{

	@SerializedName("data")
	private Data data;

	@SerializedName("meta")
	private Meta meta;

	@SerializedName("token")
	private String token;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"ModelLogin{" + 
			"data = '" + data + '\'' + 
			",meta = '" + meta + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}