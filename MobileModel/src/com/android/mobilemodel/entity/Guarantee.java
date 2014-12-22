package com.android.mobilemodel.entity;

public class Guarantee {
	private int id;
	private String name;
	
	public Guarantee() {
		// TODO Auto-generated constructor stub
		setId(0);
		setName("");
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
