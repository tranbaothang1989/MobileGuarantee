package com.suntelecom.mobilewaranty.entity;

import java.io.Serializable;

/**
 * @author ThangTB
 *
 */
public class CorrectionEntity implements Serializable{
	private int id;
	private int modelId;
	private String code;
	private String name;
	private String nameShow;
	
	public CorrectionEntity() {
		// TODO Auto-generated constructor stub
		setId(0);
		setModelId(0);
		setCode("");
		setName("");
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
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
	
	public void setNameShow(String nameShow) {
		this.nameShow = nameShow;
	}
	
	public String getNameShow() {
		return nameShow;
	}
}
