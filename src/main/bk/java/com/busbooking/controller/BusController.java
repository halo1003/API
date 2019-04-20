package com.busbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busbooking.entities.Bus;
import com.busbooking.entities.Tour;
import com.busbooking.service.BusService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/bus")
public class BusController {

	@Autowired
	BusService busService;

	/* ---------------- CREATE BUS ------------------------ */
	@PostMapping(value = "/create")
	public ResponseEntity<String> createBus(@RequestBody Bus bus, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<String>("Not validate!", HttpStatus.BAD_REQUEST);
		}
		Bus _bus = busService.save(new Bus(bus.getBusNumber(), bus.getTour()));
		if (_bus != null) {
			return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Create faild!", HttpStatus.BAD_REQUEST);
		}
	}

	/* ---------------- GET ALL BUS ------------------------ */
	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAll() {
		return new ResponseEntity<Object>(busService.findAll(), HttpStatus.OK);
	}
	
	/* ---------------- GET ALL DISTINCT BUS ------------------------ */
	@GetMapping(value = "/alldistinct")
	public ResponseEntity<List<Bus>> findAllDistinctBus() {
		List<Bus> buses = busService.findAllDistinctBus();
		return new ResponseEntity<List<Bus>>(buses, HttpStatus.OK);
	}
	
	/* ---------------- GET TOUR BY BUS ------------------------ */
	@GetMapping(value = "/b={bus_number}")
	public ResponseEntity<Object> findTourByBusnumber(@PathVariable String bus_number) {
		List<Bus> bus = busService.findTourByBusnumber(bus_number);
		if (bus != null) {
			return new ResponseEntity<Object>(bus, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Not Found Bus", HttpStatus.NO_CONTENT);
	}
	
	
	/* ---------------- GET BUS BY TOUR ------------------------ */
	@GetMapping(value = "/t={idTour}")
	public ResponseEntity<Object> findBusByIdTour(@PathVariable int idTour) {
		List<Bus> bus = busService.findBusByIdTour(idTour);
		if (bus != null) {
			return new ResponseEntity<Object>(bus, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Not Found Bus", HttpStatus.NO_CONTENT);
	}
}
