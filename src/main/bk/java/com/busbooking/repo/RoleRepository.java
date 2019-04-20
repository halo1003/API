package com.busbooking.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entities.Role;
import com.busbooking.entities.User;

@Repository 
public interface RoleRepository extends JpaRepository<Role, Integer> {

	List<Role> findById(int id);

	void save(User user);

}
