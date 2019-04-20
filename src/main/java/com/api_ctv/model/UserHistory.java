package com.api_ctv.model;

public class UserHistory {
	int idu;
	String uid;
	String mid;
	int n;
	
	public UserHistory() {
		super();
	}

	public UserHistory(int idu, String uid, String mid, int n) {
		super();
		this.idu = idu;
		this.uid = uid;
		this.mid = mid;
		this.n = n;
	}

	public int getIdu() {
		return idu;
	}

	public void setIdu(int idu) {
		this.idu = idu;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}
	
}
