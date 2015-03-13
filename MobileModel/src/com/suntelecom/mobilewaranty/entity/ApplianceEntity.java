package com.suntelecom.mobilewaranty.entity;

/**
 * @author ThangTB
 *
 */
public class ApplianceEntity {
	
	private int id;
	private int CorrectionId;
	private String code;
	private String name;
	private int appliancePrice;
	private int fee;


    private String correctionCode;
	public ApplianceEntity() {
		// TODO Auto-generated constructor stub
		setId(0);
		setCorrectionId(0);
		setCode("");
		setName("");
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCorrectionId() {
		return CorrectionId;
	}

	public void setCorrectionId(int correctionId) {
		CorrectionId = correctionId;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setFee(int fee) {
		this.fee = fee;
	}
	
	public int getFee() {
		return fee;
	}

	public void setAppliancePrice(int appliancePrice) {
		this.appliancePrice = appliancePrice;
	}
	
	public int getAppliancePrice() {
		return appliancePrice;
	}

    public void setCorrectionCode(String correctionCode) {
        this.correctionCode = correctionCode;
    }

    public String getCorrectionCode() {
        return correctionCode;
    }
}
