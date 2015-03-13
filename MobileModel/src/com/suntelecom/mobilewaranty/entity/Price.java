package com.suntelecom.mobilewaranty.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Price implements Serializable{

	private int id;
	private int guaranteeId;
	private int componentId;
	private int price;
	
	public Price() {
		// TODO Auto-generated constructor stub
		setId(0);
		setComponentId(0);
		setGuaranteeId(0);
		setPrice(0);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGuaranteeId() {
		return guaranteeId;
	}
	public void setGuaranteeId(int guaranteeId) {
		this.guaranteeId = guaranteeId;
	}
	public int getComponentId() {
		return componentId;
	}
	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
