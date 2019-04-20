package com.busbooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbooking.entities.Bus;
import com.busbooking.repo.BusRepository;

@Service
public class BusServiceImple implements BusService {

	@Autowired
	private BusRepository busRepository;

	@Autowired
	private SeatService seatService;

	/* ---------------- CREATE BUS ------------------------ */
	@Override
	public Bus save(Bus bus) {
		busRepository.save(bus);
		seatService.save(busRepository.getOne(bus.getId()));
		return bus;
	}

	/* ---------------- FIND BUS ------------------------ */
	@Override
	public List<Bus> findAll() {
		return busRepository.findAllByOrderByIdDesc();
	}

	/* ---------------- FIND ALL DISTINCT BUS ------------------------ */
	@Override
	public List<Bus> findAllDistinctBus() {
		return busRepository.findAllDistinctBus();
	}

	/* ---------------- FIND TOUR BY BUS ------------------------ */
	@Override
	public List<Bus> findTourByBusnumber(String bus_number) {
		return busRepository.findTourByBusnumber(bus_number);
	}

	/* ---------------- FIND BUS BY ID TOUR ------------------------ */
	@Override
	public List<Bus> findBusByIdTour(int idTour) {
		return busRepository.findBusByIdTour(idTour);
	}

	@Override
	public Bus findById(int idBus) {
		return busRepository.findbyId(idBus);
	}
}
