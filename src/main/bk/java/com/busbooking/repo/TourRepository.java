package com.busbooking.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.busbooking.entities.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {

	Page<Tour> findAllByOrderByIdDesc(Pageable bageable);
	
	@Query("SELECT t FROM Tour t WHERE t.id = :idTour")
	Tour findOne(@Param("idTour") int idTour);
	
	@Query("SELECT t FROM Tour t WHERE t.startPlace like %:startPlace%")
	List<Tour> findByStartPlaceContaining(String startPlace);

	@Query("SELECT t FROM Tour t WHERE t.startPlace = :startPlace")
	List<Tour> findTourByStartPlace(@Param("startPlace") String startPlace);
	
	@Query("SELECT t.endPlace FROM Tour t WHERE t.startPlace = :startPlace")
	List<Tour> findEndPlaceByStartPlace(@Param("startPlace") String startPlace);
	
	@Query("SELECT t.startTime FROM Tour t WHERE t.startPlace = :startPlace AND t.endPlace = :endPlace")
	List<Tour> findStartTimeByStartPlace(@Param("startPlace") String startPlace, @Param("endPlace") String endPlace);
	
	@Query("SELECT DISTINCT t.startPlace FROM Tour t")
	List<Tour> findAllStartPlace();
	
	@Query("SELECT t FROM Tour t WHERE t.startPlace = :startPlace AND t.startTime > :startTime AND t.endPlace = :endPlace ")
	Page<Tour> findTourByParam(@Param("startPlace") String startPlace, @Param("endPlace") String endPlace, @Param("startTime") LocalDateTime startTime, Pageable pageable);
	
}
