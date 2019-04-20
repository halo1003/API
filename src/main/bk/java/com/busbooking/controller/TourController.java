package com.busbooking.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busbooking.entities.Tour;
import com.busbooking.service.TourService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tour")
public class TourController {

	@Autowired
	TourService tourService;

	/* ---------------- GET ALL TOUR ------------------------ */
	@GetMapping(value = { "/all" })
	public ResponseEntity<Object> getTour(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
			@RequestParam(name = "e", required = false, defaultValue = "endPlace") String element) {

		Sort sortable = null;
		if (sort.equals("asc")) {
			sortable = Sort.by(element).ascending();
		}
		if (sort.equals("desc")) {
			sortable = Sort.by(element).descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		return new ResponseEntity<Object>(tourService.findAll(pageable), HttpStatus.OK);
	}

	/* ---------------- GET TOUR BY ID ------------------------ */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> findTour(@PathVariable("id") int idTour) {
		Optional<Tour> tour = tourService.findById(idTour);

		if (tour != null) {
			return new ResponseEntity<Object>(tour, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
	}

	/* ---------------- GET TOUR BY STARTPLACE ------------------------ */
	@GetMapping(value = "/startplace")
	public ResponseEntity<List<Tour>> findByStartPlaceContaining(
			@RequestParam(value = "s", required = false) String startPlace) {
		List<Tour> tour = tourService.findByStartPlaceContaining(startPlace);
		if (tour != null) {
			return new ResponseEntity<List<Tour>>(tour, HttpStatus.OK);
		}
		return new ResponseEntity<List<Tour>>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/getallstartplace")
	public ResponseEntity<List<Tour>> findAllStartPlace() {
		List<Tour> tour = tourService.findAllStartPlace();
		return new ResponseEntity<List<Tour>>(tour, HttpStatus.OK);
	}

	/* ---------------- GET ENDPLACE BY STARTPLACE ------------------------ */
	@GetMapping(value = "/endplace")
	public ResponseEntity<List<Tour>> findEndPlaceByStartPlace(
			@RequestParam(value = "s", required = false) String startPlace) {
		List<Tour> tour = tourService.findEndPlaceByStartPlace(startPlace);
		if (tour != null) {
			return new ResponseEntity<List<Tour>>(tour, HttpStatus.OK);
		}
		return new ResponseEntity<List<Tour>>(HttpStatus.NOT_FOUND);
	}

	/* ---------------- GET STARTTIME BY STARTPLACE ------------------------ */
	@GetMapping(value = "/starttime")
	public ResponseEntity<List<Tour>> findStartTimeByStartPlace(
			@RequestParam(value = "s", required = false) String startPlace,
			@RequestParam(value = "e", required = false) String endPlace) {
		List<Tour> tour = tourService.findStartTimeByStartPlace(startPlace, endPlace);
		if (tour != null) {
			return new ResponseEntity<List<Tour>>(tour, HttpStatus.OK);
		}
		return new ResponseEntity<List<Tour>>(HttpStatus.NOT_FOUND);
	}

	/* ---------------- GET TRIP BY PARAM ------------------------ */
	@GetMapping(value = "/trip")
	public ResponseEntity<Object> findTourByParam(@RequestParam(value = "s", required = false) String startPlace,
			@RequestParam(value = "e", required = false) String endPlace,
			@RequestParam("t") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
			@RequestParam(name = "e", required = false, defaultValue = "name") String element) {

		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by(element).ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by(element).descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		return new ResponseEntity<Object>(tourService.findTourByParam(startPlace, endPlace, startTime, pageable),
				HttpStatus.OK);
	}

	/* ---------------- CREATE USER TOUR------------------------ */
	@PostMapping(value = "/create")
	public Tour createTour(@RequestBody Tour tour, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return null;
		}
		Tour _tour = tourService.save(new Tour(tour.getStartPlace(), tour.getEndPlace(), tour.getStartTime(),
				tour.getEndTime(), tour.isAcitve()));
		return _tour;
	}

	/* ---------------- UPDATE TOUR------------------------ */
	@PutMapping("/{id}/update")
	public ResponseEntity<Object> changeTour(@PathVariable("id") int idTour, @RequestBody Tour tour) {

		Optional<Tour> tourData = tourService.findById(idTour);
		if (tourData.isPresent()) {
			Tour _tour = tourData.get();
			_tour.setStartPlace(tour.getStartPlace());
			_tour.setEndPlace(tour.getEndPlace());
			_tour.setStartTime(tour.getStartTime());
			_tour.setEndTime(tour.getEndTime());
			_tour.setTotalSeats(tour.getTotalSeats());
			_tour.setAcitve(tour.isAcitve());
			return new ResponseEntity<>(tourService.save(_tour), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/* ---------------- DELETE TOUR ------------------------ */
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<String> deleteTour(@PathVariable("id") int id) {
		System.out.println("Delete tour with ID = " + id + "...");

		tourService.deleteById(id);

		return new ResponseEntity<>("Tour has been deleted!", HttpStatus.OK);
	}

	/* ---------------- DELETE ALL TOUR ------------------------ */
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteAll() {

		tourService.deleteAll();

		return new ResponseEntity<>("All tour have been deleted!", HttpStatus.OK);
	}
}
