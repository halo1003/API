package com.api_ctv.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clustering")
public class Cluster {
	
	@Id
	@Column(name = "idclustering")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idcluster;

	@Column(name = "uindex")
	private String uindex;

	@Column(name = "have_radius")
	private double have_radius;
	
	@Column(name = "with_uindex")
	private String with_uindex;

	public Cluster() {
		super();
	}

	public Cluster(int idcluster, String uindex, double have_radius, String with_uindex) {
		super();
		this.idcluster = idcluster;
		this.uindex = uindex;
		this.have_radius = have_radius;
		this.with_uindex = with_uindex;
	}

	public int getIdcluster() {
		return idcluster;
	}

	public void setIdcluster(int idcluster) {
		this.idcluster = idcluster;
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
