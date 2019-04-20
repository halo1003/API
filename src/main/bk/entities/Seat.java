package com.busbooking.entities;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "seat")
public class Seat {

	@Id
	@Column(name = "idseat")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idSeat;

	@Column(name = "numberseat")
	private int numberSeat;

	@Column(name = "active")
	private boolean active;

	@OneToOne(mappedBy = "seat")
	@JsonIgnore
	private Ticket ticket;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bus_idbus")
	private Bus bus;

	public Seat(int numberSeat, boolean active, Bus bus) {
		this.numberSeat = numberSeat;
		this.active = active;
		this.bus = bus;
	}

	public Seat() {
	}

	public int getNumberSeat() {
		return numberSeat;
	}

	public void setNumberSeat(int numberSeat) {
		this.numberSeat = numberSeat;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getIdSeat() {
		return idSeat;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
}
