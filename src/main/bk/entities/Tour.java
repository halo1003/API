package com.busbooking.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tour")
public class Tour {

	@Id
	@Column(name = "idtour")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "startplace")
	private String startPlace;

	@Column(name = "endplace")
	private String endPlace;

	@Column(name = "starttime")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime startTime;

	@Column(name = "endtime")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime endTime;

	@Column(name = "totalseats")
	private int totalSeats;

	@Column(name = "isavailable")
	private boolean acitve;

	public Tour() {
	}

	public Tour(String startPlace, String endPlace, LocalDateTime startTime, LocalDateTime endTime, boolean acitve) {
		this.startPlace = startPlace;
		this.endPlace = endPlace;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalSeats = 45;
		this.acitve = acitve;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getEndPlace() {
		return endPlace;
	}

	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public boolean isAcitve() {
		return acitve;
	}

	public void setAcitve(boolean acitve) {
		this.acitve = acitve;
	}

	public int getId() {
		return id;
	}

}
