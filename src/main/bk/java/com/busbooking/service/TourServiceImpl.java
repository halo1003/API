package com.busbooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.busbooking.entities.Tour;
import com.busbooking.repo.TourRepository;

@Service
public class TourServiceImpl implements TourService {

	@Autowired
	private TourRepository tourRepository;

	/* ---------------- FIND TOUR ------------------------ */
	@Override
	public Page<Tour> findAll(Pageable bageable) {
		return tourRepository.findAllByOrderByIdDesc(bageable);
	}
	
	/* ---------------- FIND ALL STARTPLACE ------------------------ */
	@Override
	public List<Tour> findAllStartPlace(){
		return tourRepository.findAllStartPlace();
	}
	
	/* ---------------- FIND TOUR BY STARTPLACE CONTAINING ------------------------ */
	@Override
	public
	List<Tour> findByStartPlaceContaining(String startPlace){
		return tourRepository.findByStartPlaceContaining(startPlace);
	}

	/* ---------------- FIND TOUR BY START PLACE ------------------------ */
	@Override
	public List<Tour> findTourByStartPlace(String startPlace) {
		return tourRepository.findTourByStartPlace(startPlace);
	}

	/* ---------------- FIND ENDPLACE BY STARTPLACE ------------------------ */
	@Override
	public List<Tour> findEndPlaceByStartPlace(String startPlace) {
		return tourRepository.findEndPlaceByStartPlace(startPlace);
	}

	/* ---------------- FIND STARTTIME BY STARTPLACE ------------------------ */
	@Override
	public List<Tour> findStartTimeByStartPlace(String startPlace, String endPlace) {
		return tourRepository.findStartTimeByStartPlace(startPlace, endPlace);
	}
	
	/* ---------------- FIND TOUR BY PARAM ------------------------ */
	@Override
	public Page<Tour> findTourByParam(String startPlace, String endPlace, LocalDateTime startTime, Pageable pageable){
		return tourRepository.findTourByParam(startPlace, endPlace, startTime, pageable);
	}

	/* ---------------- CREATE TOUR ------------------------ */
	@Override
	public Tour save(Tour seat) {
		return tourRepository.save(seat);
	}

	/* ---------------- FIND ID TOUR ------------------------ */
	@Override
	public Optional<Tour> findById(int idTour) {
		return tourRepository.findById(idTour);
	}

	/* ---------------- DELETE ALL TOUR ------------------------ */
	@Override
	public void deleteAll() {
		tourRepository.deleteAll();
	}

	/* ---------------- DELETE BY ID TOUR ------------------------ */
	@Override
	public void deleteById(int id) {
		tourRepository.deleteById(id);
	}

	@Override
	public Tour findOne(int idTour) {
		return tourRepository.findOne(idTour);
	}

}
