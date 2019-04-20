package com.busbooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.busbooking.service.SeatService;

@SpringBootApplication
public class BeBbtsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeBbtsApplication.class, args);
		
	}
}
