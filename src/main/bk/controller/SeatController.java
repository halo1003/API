package com.busbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busbooking.entities.Seat;
import com.busbooking.service.SeatService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/seat")
public class SeatController {

	@Autowired
	SeatService seatService;

	/* ---------------- GET SEAT BY BUS ------------------------ */
	@RequestMapping(value = "/b={idBus}", method = RequestMethod.GET)
	public ResponseEntity<Object> findSeatByIdBus(@PathVariable int idBus) {
		List<Seat> seat = seatService.findSeatByIdBus(idBus);
		if (seat != null) {
			return new ResponseEntity<Object>(seat, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Not Found Seat", HttpStatus.NO_CONTENT);
	}

	/* ---------------- GET A SEAT BY BUS ------------------------ */
	@RequestMapping(value = "/b={idBus}/s={numberSeat}", method = RequestMethod.GET)
	public ResponseEntity<Object> findSeatInBusById(@PathVariable("idBus") int idBus,
			@PathVariable("numberSeat") int numberSeat) {
		Seat seat = seatService.findByIdByIdBus(idBus, numberSeat);
		if (seat != null) {
			return new ResponseEntity<Object>(seat, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Not Found Seat", HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/empty")
	public ResponseEntity<List<Seat>> findEmptySeatForTicket(@RequestParam("idT") int idTour,
			@RequestParam("idB") int idBus) {
		List<Seat> _seat = seatService.findEmptySeatForTicket(idTour, idBus);
		if (_seat.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Seat>>(_seat, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/booked")
	public ResponseEntity<List<Seat>> findBookedSeatForTicket(@RequestParam("idT") int idTour,
			@RequestParam("idB") int idBus) {
		List<Seat> _seat = seatService.findBookedSeatForTicket(idTour, idBus);
		if (_seat.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Seat>>(_seat, HttpStatus.OK);
		}
	}

}
