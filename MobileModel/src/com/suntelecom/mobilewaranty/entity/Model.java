package com.suntelecom.mobilewaranty.entity;

import java.io.Serializable;

/**
 * @author ThangTB
 *
 */
public class Model implements Serializable{
	private int id;
	private String brand;
	private String modelName;
	private String createdAt;
	
	public Model() {
		// TODO Auto-generated constructor stub
		setId(0);
		setBrand("");
		setModelName("");
		setCreatedAt("");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelCode) {
		this.modelName = modelCode;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	
}
