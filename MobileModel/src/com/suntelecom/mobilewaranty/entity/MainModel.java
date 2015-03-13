package com.suntelecom.mobilewaranty.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MainModel implements Serializable{

	private int id;
	
	private String modelCode;
	
	private String correctionCode;
	private String correctionName;
	
	private String applianceCode;
	private String applianceName;
	
	private int appliancePrice;
	private int fee;
	
	public MainModel() {
		// TODO Auto-generated constructor stub
		
		setApplianceCode("");
		setApplianceName("");
		
		setCorrectionCode("");
		setCorrectionName("");
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	
	public String getModelCode() {
		return modelCode;
	}
	
	public String getCorrectionCode() {
		return correctionCode;
	}
	public void setCorrectionCode(String correctionCode) {
		this.correctionCode = correctionCode;
	}
	public String getCorrectionName() {
		return correctionName;
	}
	public void setCorrectionName(String correctionName) {
		this.correctionName = correctionName;
	}
	public String getApplianceCode() {
		return applianceCode;
	}
	public void setApplianceCode(String componentCode) {
		this.applianceCode = componentCode;
	}
	public String getApplianceName() {
		return applianceName;
	}
	public void setApplianceName(String componentName) {
		this.applianceName = componentName;
	}
	
	public void setAppliancePrice(int appliancePrice) {
		this.appliancePrice = appliancePrice;
	}
	
	public void setFee(int fee) {
		this.fee = fee;
	}
	
	public int getAppliancePrice() {
		return appliancePrice;
	}
	
	public int getFee() {
		return fee;
	}
	
}
