package com.busbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.busbooking.entities.Ticket;

public interface TicketService {

	public Ticket save(Ticket ticket);

	public List<Ticket> getAllTicket();
	
	public Page<Ticket> findTicket(Pageable pageable);

	public Optional<Ticket> findOne(int id);

//	public Optional<Ticket> findSeatForTicket(int idTour, int idBus, int numberSeat);

	public void deleteAll();

	public void deleteTicket(int id);

	public List<Ticket> findTicketByUserId(int userId);

	public List<Ticket> findBookedSeat(int idTour, int idBus);
}
