package com.busbooking.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.busbooking.entities.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

	/* ---------------- FIND SEAT IN BUS ------------------------ */
	@Query(value = "SELECT * FROM seat s WHERE s.bus_idbus = :idbus", nativeQuery = true)
	List<Seat> findSeatByIdBus(@Param("idbus") int idBus);

	/* ---------------- FIND ID SEAT BY ID BUS ------------------------ */
	@Query(value = "SELECT * FROM seat s WHERE s.bus_idbus = :idbus and s.numberseat = :numberSeat", nativeQuery = true)
	Seat findByIdByIdBus(@Param("idbus") int idBus, @Param("numberSeat") int numberSeat);

	/* ---------------- SET ACIVE SEAT ------------------------ */
	@Query(value = "UPDATE seat s SET s.active = :active WHERE s.numberseat = :numberSeat", nativeQuery = true)
	List<Seat> bookingSeat(@Param("active") boolean active, @Param("numberSeat") int numberSeat);

	/* ---------------- FIND TOUR, BUS, SEAT ------------------------ */
	@Query(value = "SELECT s.*, b.*, t.* FROM seat s INNER JOIN bus b ON s.bus_idbus = b.idbus INNER JOIN tour t ON b.tour_idtour = t.idtour WHERE t.idtour = :idTour AND b.idbus = :idBus AND s.numberseat = :numberSeat", nativeQuery = true)
	Optional<Seat> findSeatForTicket(@Param("idTour") int idTour, @Param("idBus") int idBus,
			@Param("numberSeat") int numberSeat);

	/* ---------------- FIND TOUR, BUS, SEAT ------------------------ */
	@Query(value = "SELECT s.* FROM seat s INNER JOIN bus b ON s.bus_idbus = b.idbus INNER JOIN tour t ON b.tour_idtour = t.idtour WHERE t.idtour = :idTour AND b.idbus = :idBus AND s.numberseat = :numberSeat", nativeQuery = true)
	Seat findSeatInTicket(@Param("idTour") int idTour, @Param("idBus") int idBus,
			@Param("numberSeat") String numberSeat);
	
	/* ---------------- FIND TOUR, BUS, SEATS ------------------------ */
	@Query(value = "SELECT s.* FROM seat s INNER JOIN bus b ON s.bus_idbus = b.idbus INNER JOIN tour t ON b.tour_idtour = t.idtour WHERE t.idtour = :idTour AND b.idbus = :idBus", nativeQuery = true)
	List<Seat> findSeatsForTicket(@Param("idTour") int idTour, @Param("idBus") int idBus);

	/* ---------------- FIND SEAT EMPTY ------------------------ */
	@Query(value = "SELECT s.*, b.*, t.* " + "FROM seat s " + "INNER JOIN bus b " + "ON s.bus_idbus = b.idbus "
			+ "INNER JOIN tour t " + "ON b.tour_idtour = t.idtour " + "WHERE t.idtour = :idTour "
			+ "AND b.idbus = :idBus " + "AND s.idseat NOT IN " + "		(" + "        select  tk.seat_idseat "
			+ "        from    ticket tk " + "        where   tk.tour_idtour = :idTour "
			+ "                and tk.bus_idbus = :idBus " + "        )", nativeQuery = true)
	List<Seat> findEmptySeatForTicket(@Param("idTour") int idTour, @Param("idBus") int idBus);

	/* ---------------- FIND SEAT BOOKED------------------------ */
	@Query(value = "SELECT s.*, b.*, t.* " + "FROM seat s " + "INNER JOIN bus b " + "ON s.bus_idbus = b.idbus "
			+ "INNER JOIN tour t " + "ON b.tour_idtour = t.idtour " + "WHERE t.idtour = :idTour "
			+ "AND b.idbus = :idBus " + "AND s.idseat IN " + "		(" + "        select  tk.seat_idseat "
			+ "        from    ticket tk " + "        where   tk.tour_idtour = :idTour "
			+ "                and tk.bus_idbus = :idBus " + "        )", nativeQuery = true)
	List<Seat> findBookedSeatForTicket(@Param("idTour") int idTour, @Param("idBus") int idBus);
}
