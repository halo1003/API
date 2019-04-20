//package com.busbooking.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.busbooking.entities.User;
//import com.busbooking.repo.RoleRepository;
//import com.busbooking.repo.UserRepository;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private RoleRepository roleRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//		User user = userRepository.findByUsername(userName);
//
//		if (user == null) {
//			System.out.println("User not found! " + userName);
//			throw new UsernameNotFoundException("User " + userName + " was not found in the database");
//		}
//
//		System.out.println("Found User: " + user);
//
//		// [ROLE_CUSTOMER, ROLE_SELLER, ROLE_ADMIN]
//		List<String> role = roleRepository.findByName(user.getId());
//
//		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//		for (String rolename : role) {
//			// ROLE_CUSTOMER, ROLE_SELLER, ROLE_ADMIN
//			GrantedAuthority authority = new SimpleGrantedAuthority(rolename);
//			grantList.add(authority);
//
//		}
//
//		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
//				user.getPassword(), grantList);
//
//		return userDetails;
//	}
//}
