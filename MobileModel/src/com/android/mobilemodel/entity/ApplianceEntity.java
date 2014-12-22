package com.android.mobilemodel.entity;

/**
 * @author ThangTB
 *
 */
public class ApplianceEntity {
	
	private String id;
	private String CorrectionId;
	private String name;
	private int price;
	public ApplianceEntity() {
		// TODO Auto-generated constructor stub
		setId("");
		setCorrectionId("");
		setName("");
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCorrectionId() {
		return CorrectionId;
	}

	public void setCorrectionId(String correctionId) {
		CorrectionId = correctionId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}

}
