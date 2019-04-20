package com.busbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.busbooking.entities.Role;
import com.busbooking.entities.User;

public interface UserService {
	
	/* ---------------------- USER ------------------------ */
	public boolean add(User user);
	
	public List<User> findAll();

	public Optional<User> findById(int id);
//	public User findById(int id);
	
	public List<User> findByName(String name);
	
	Page<User> findUser(Pageable pageable);

	public void deleteById(int id);

	public User loadUserByUsername(String username);
	
	public User loadUserByToken(String token);
	
	

	public boolean checkLogin(User user);

	/* ---------------------- ROLE ------------------------ */
	public List<Role> findByIdRole(int id);
}
