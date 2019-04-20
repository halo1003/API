package com.busbooking.controller;

import java.util.List;
import java.util.Optional;

import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busbooking.entities.Seat;
import com.busbooking.entities.Ticket;
import com.busbooking.entities.User;
import com.busbooking.repo.TicketRepository;
import com.busbooking.service.BusService;
import com.busbooking.service.JwtService;
import com.busbooking.service.SeatService;
import com.busbooking.service.TicketService;
import com.busbooking.service.TourService;
import com.busbooking.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	TicketService ticketService;

	@Autowired
	TourService tourService;

	@Autowired
	SeatService seatService;

	@Autowired
	BusService busService;

	@Autowired
	JwtService jwtService;

	@Autowired
	UserService userService;

	/* ---------------- GET ALL TICKET ------------------------ */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<Ticket>> getAllTickets() {
		return new ResponseEntity<List<Ticket>>(ticketService.getAllTicket(), HttpStatus.OK);
	}

	/* ---------------- GET TICKET ------------------------ */
	@GetMapping(value = "/{id}")
	public Optional<Ticket> findTicket(@PathVariable("id") int id) {
		System.out.println("Find Tour with ID = " + id + "...");
		Optional<Ticket> ticket = ticketService.findOne(id);
		return ticket;
	}

	/*------------------- findTicketByUserId -------------------------*/
	@GetMapping(value = "/user")
	public List<Ticket> findTicketByUserId(@RequestParam(value = "id", required = false) int userId) {
		List<Ticket> ticket = ticketService.findTicketByUserId(userId);
		return ticket;
	}

	/*------------------- FIND BOOKED SEATS -------------------------*/
	@GetMapping(value = "/{idTour}/{idBus}")
	public ResponseEntity<Object> findBookedSeat(@PathVariable("idTour") int idTour, @PathVariable("idBus") int idBus) {
		List<Ticket> ticket = ticketService.findBookedSeat(idTour, idBus);
		if (ticket != null) {
			return new ResponseEntity<Object>(ticket, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	/* ---------------- PAGEABLE TICKET BY TIME ------------------------ */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Object> listTicket(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(name = "e", required = false, defaultValue = "timecreate") String element) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by(element).ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by(element).descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		return new ResponseEntity<Object>(ticketRepository.findTicket(pageable), HttpStatus.OK);
	}

	/* ---------------- CREATE TICKET ------------------------ */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> createTicket(@RequestBody Ticket ticket) {
		Ticket _ticket = ticketService
				.save(new Ticket(ticket.getTour(), ticket.getBus(), ticket.getUser(), ticket.getSeat()));
		if (_ticket != null) {
			return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Create faild!", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/booking", method = RequestMethod.POST)
	public ResponseEntity<Object> bookingTicket(@RequestParam(value = "t", required = false) int idTour,
			@RequestParam(value = "b", required = false) int idBus,
			@RequestParam(value = "s", required = false) String numberSeat,
			@RequestParam(value = "tk", required = false) String token) {

		Seat _seat = seatService.findSeatInTicket(idTour, idBus, numberSeat);
		if (_seat!=null) {
			List<Seat> findEmptySeatForTicket = seatService.findEmptySeatForTicket(idTour, idBus);
			if (!findEmptySeatForTicket.contains(_seat)) {
				return new ResponseEntity<>("Book faild by Seat.", HttpStatus.NOT_FOUND);
			} else {
				User user = userService.loadUserByToken(token);
				return new ResponseEntity<Object>(
						ticketRepository.save(
								new Ticket(	tourService.findOne(idTour), busService.findById(idBus), user, _seat)),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Book faild.", HttpStatus.NOT_FOUND);
	}

	/* ---------------- UPDATE TICKET ------------------------ */
	@RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateTicket(@PathVariable("id") int id, @RequestBody Ticket ticket) {
		Optional<Ticket> ticketData = ticketService.findOne(id);
		if (ticketData.isPresent()) {
			Ticket _ticket = ticketData.get();
			System.out.println(_ticket);
			_ticket.setTimecreate(ticket.getTimecreate());
			_ticket.setSeat(ticket.getSeat());
			_ticket.setActive(ticket.isActive());
			_ticket.setBus(ticket.getBus());
			_ticket.setTour(ticket.getTour());
			_ticket.setUser(ticket.getUser());
			ticketService.save(_ticket);
			return new ResponseEntity<>("Updated!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Update faild", HttpStatus.NOT_FOUND);
		}
	}

	/* ---------------- DELETE TICKET ------------------------ */
	@RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTicketById(@PathVariable int id) {
		Optional<Ticket> ticket = ticketService.findOne(id);
		if (ticket != null) {
			ticketService.deleteTicket(id);
			return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Deleted faild!", HttpStatus.NOT_FOUND);
	}

	/* ---------------- DELETE ALL TICKET ------------------------ */
	@RequestMapping(value = "/remove", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAllTicket() {
		ticketService.deleteAll();
		return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
	}
}
