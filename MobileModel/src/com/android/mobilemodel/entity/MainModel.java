package com.android.mobilemodel.entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class MainModel implements Serializable{

	private int id;
	private String correctionCode;
	private String correctionName;
	private String componentCode;
	private String componentName;
	
	private ArrayList<Price> prices;
	
	public MainModel() {
		// TODO Auto-generated constructor stub
		setComponentCode("");
		setComponentName("");
		setCorrectionCode("");
		setCorrectionName("");
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getComponentCode() {
		return componentCode;
	}
	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	
	
	/**
	 * @param prices
	 */
	public void setPrices(ArrayList<Price> prices) {
		this.prices = prices;
	}
	
	/**
	 * @return
	 */
	public ArrayList<Price> getPrices() {
		return prices;
	}
	
}
