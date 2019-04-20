package com.busbooking.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bus")
public class Bus {

	@Id
	@Column(name = "idbus")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "busnumber")
	private String busNumber;

	@Column(name = "totalseat")
	private int totalSeat = 45;

	@OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Seat> seats;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tour_idtour")
	private Tour tour;

	public Bus() {
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public Bus(String busNumber, Tour tour) {
		this.busNumber = busNumber;
		this.tour = tour;
	}

	public String getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}

	public int getId() {
		return id;
	}

	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	public int getTotalSeat() {
		return totalSeat;
	}

	public void setTotalSeat(int totalSeat) {
		this.totalSeat = totalSeat;
	}

}
