package com.api_ctv.model;

public class Item {
	
	int id;
	String uindex;
	double have_radius;
	String with_uindex;

	public Item() {
		
	}

	public Item(int id, String uindex, double have_radius, String with_uindex) {		
		this.id = id;
		this.uindex = uindex;
		this.have_radius = have_radius;
		this.with_uindex = with_uindex;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUindex() {
		return uindex;
	}

	public void setUindex(String uindex) {
		this.uindex = uindex;
	}

	public double getHave_radius() {
		return have_radius;
	}

	public void setHave_radius(double have_radius) {
		this.have_radius = have_radius;
	}

	public String getWith_uindex() {
		return with_uindex;
	}

	public void setWith_uindex(String with_uindex) {
		this.with_uindex = with_uindex;
	}

}
