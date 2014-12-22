package com.android.mobilemodel.entity;

/**
 * @author ThangTB
 *
 */
public class Model {
	private int id;
	private String brand;
	private String modelCode;
	private String createdAt;
	
	public Model() {
		// TODO Auto-generated constructor stub
		setId(0);
		setBrand("");
		setModelCode("");
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

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	
}
