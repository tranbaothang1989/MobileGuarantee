package com.android.mobilemodel.entity;

/**
 * @author ThangTB
 *
 */
public class CorrectionEntity {
	private String id;
	private int modelId;
	private String name;
	
	public CorrectionEntity() {
		// TODO Auto-generated constructor stub
		setId("");
		setModelId(0);
		setName("");
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
