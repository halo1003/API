package com.busbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.busbooking.entities.Role;
import com.busbooking.entities.User;
import com.busbooking.repo.RoleRepository;
import com.busbooking.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/* ---------------------- USER ------------------------ */

	/* ---------------- CREATE USER ------------------------ */
	@Override
	public boolean add(User user) {
		userRepository.save(user);
		return true;
	}

	/* ---------------- FIND ALL USER ------------------------ */
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/* ---------------- FIND USER BY ID ------------------------ */
	@Override
	public Optional<User> findById(int id) {
		return userRepository.findById(id);
	}

	/* ---------------- FIND USER BY NAME ------------------------ */
	@Override
	public List<User> findByName(String name) {
		return userRepository.findByName(name);
	}

	/* ---------------- PAGEABLE USER BY NAME ------------------------ */
	@Override
	public Page<User> findUser(Pageable pageable) {
		return userRepository.findUser(pageable);
	}

	/* ---------------- DELETE USER BY ID ------------------------ */
	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}

	/* ---------------- LOAD USER BY NAME ------------------------ */
	@Override
	public User loadUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	/* ---------------- LOAD USER BY TOKEN ------------------------ */
	@Override
	public User loadUserByToken(String token) {
		String _token = jwtService.getUsernameFromToken(token);
		return userRepository.findByUsernameByToken(_token);
	}

	/* ---------------- CHECK LOGIN USER ------------------------ */
	@Override
	public boolean checkLogin(User user) {
		User _user = userRepository.findByUsername(user.getUsername());
		if (_user != null) {
			if (bCryptPasswordEncoder.matches(user.getPassword(), _user.getPassword())) {
				return true;
			}
		}
		return false;
	}

	/* ---------------------- ROLE ------------------------ */

	/* ---------------- FIND ROLE BY ID ------------------------ */
	@Override
	public List<Role> findByIdRole(int id) {
		return roleRepository.findById(id);
	}

}