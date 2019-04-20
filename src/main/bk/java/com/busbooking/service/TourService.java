package com.busbooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.busbooking.entities.Tour;

public interface TourService {

	public Tour save(Tour seat);
	
	public List<Tour> findByStartPlaceContaining(String startPlace);
	
	public List<Tour> findTourByStartPlace(String startPlace);

	public List<Tour> findEndPlaceByStartPlace(String startPlace);

	public List<Tour> findStartTimeByStartPlace(String startPlace, String endPlace);
	
	public Page<Tour> findTourByParam(String startPlace, String endPlace, LocalDateTime startTime, Pageable pageable);	
	
	public Page<Tour> findAll(Pageable bageable);

	public Optional<Tour> findById(int idTour);

	public void deleteAll();

	public void deleteById(int id);

	public List<Tour> findAllStartPlace();

	public Tour findOne(int idTour);

	

}
