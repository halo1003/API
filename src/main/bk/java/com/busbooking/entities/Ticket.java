package com.busbooking.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "ticket")
public class Ticket {

	@Id
	@Column(name = "idticket")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "timecreate")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime timecreate;

	@Column(name = "isavailable")
	private boolean active;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "seat_idseat")
	private Seat seat;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tour_idtour", nullable = false)
	private Tour tour;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bus_idbus", nullable = false)
	private Bus bus;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Ticket() {
	}

	public Ticket(Tour tour, Bus bus, User user, Seat seat) {
		this.timecreate = LocalDateTime.now();
		this.active = true;
		this.tour = tour;
		this.bus = bus;
		this.user = user;
		this.seat = seat;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat numberSeat) {
		this.seat = numberSeat;
	}

	public boolean Cancel() {
		return this.active = false;
	}

	
	public LocalDateTime getTimecreate() {
		return timecreate;
	}

	public void setTimecreate(LocalDateTime timecreate) {
		this.timecreate = timecreate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}
}
