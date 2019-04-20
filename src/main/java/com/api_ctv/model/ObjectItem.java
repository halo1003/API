package com.api_ctv.model;

import java.util.Comparator;

public class ObjectItem{
	String uid;
	double radius;
	
	public ObjectItem() {
		super();
	}

	public ObjectItem(String uid, double radius) {
		super();
		this.uid = uid;
		this.radius = radius;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
}
