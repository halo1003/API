package com.busbooking.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.busbooking.entities.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {

	List<Bus> findAllByOrderByIdDesc();
	/* ---------------- FIND ALL DISTINCT BUS ------------------------ */
	@Query("SELECT DISTINCT b.busNumber FROM Bus b")
	List<Bus> findAllDistinctBus();
	
	@Query("SELECT b FROM Bus b WHERE b.id = :idBus")
	Bus findbyId(int idBus);
	
	/* ---------------- FIND TOUR IN BUS ------------------------ */
	@Query(value = "SELECT * FROM bus b WHERE b.busNumber = :bus_number", nativeQuery = true)
	List<Bus> findTourByBusnumber(@Param("bus_number") String idTour);

	/* ---------------- FIND BUS IN TOUR ------------------------ */
	@Query(value = "SELECT * FROM bus b WHERE b.tour_idtour = :idTour", nativeQuery = true)
	List<Bus> findBusByIdTour(@Param("idTour") long idTour);
	
}
