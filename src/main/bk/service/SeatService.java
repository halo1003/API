package com.busbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbooking.entities.Bus;
import com.busbooking.entities.Seat;
import com.busbooking.repo.SeatRepository;

@Service
public class SeatService {

	@Autowired
	private SeatRepository seatRepository;

	/* ---------------- CREATE SEAT IN BUS ------------------------ */
	public void save(Bus bus) {
		int i;
		for (i = 1; i <= 45; i++) {
			seatRepository.save(new Seat(i, false, bus));
		}
	}

	/* ---------------- FIND SEAT IN BUS ------------------------ */
	public List<Seat> findSeatByIdBus(int idBus) {
		return seatRepository.findSeatByIdBus(idBus);
	}

	/* ---------------- FIND ID SEAT BY ID BUS ------------------------ */
	public Seat findByIdByIdBus(int idBus, int numberSeat) {
		return seatRepository.findByIdByIdBus(idBus, numberSeat);
	}

	/* ---------------- FIND SEAT DEPEND TICKET ------------------------ */
	public Optional<Seat> findSeatForTicket(int idTour, int idBus, int numberSeat) {
		return seatRepository.findSeatForTicket(idTour, idBus, numberSeat);
	}

	/* ---------------- FIND LIST SEAT DEPEND TICKET ------------------------ */
	public List<Seat> findSeatsForTicket(int idTour, int idBus) {
		return seatRepository.findSeatsForTicket(idTour, idBus);
	}

	/* ---------------- SET ACIVE SEAT ------------------------ */
	public List<Seat> bookingSeat(boolean active, int numberSeat) {
		return seatRepository.bookingSeat(active, numberSeat);
	}
	
	/* ---------------- FIND EMPTY SEAT TICKET ------------------------ */
	public List<Seat> findEmptySeatForTicket(int idTour, int idBus){
		return seatRepository.findEmptySeatForTicket(idTour, idBus);
	}
	
	/* ---------------- FIND SEAT BOOKED TICKET ------------------------ */
	public List<Seat> findBookedSeatForTicket(int idTour, int idBus){
		return seatRepository.findBookedSeatForTicket(idTour, idBus);
	}

	public Seat findSeatInTicket(int idTour, int idBus, String numberSeat) {
		return seatRepository.findSeatInTicket(idTour, idBus, numberSeat);
	}
}
