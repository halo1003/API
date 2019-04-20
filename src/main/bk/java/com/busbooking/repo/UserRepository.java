package com.busbooking.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.busbooking.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	User findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	User findByUsernameByToken(String username);

	Page<User> findAll(Specification<User> spec, Pageable pageable);

	// @Query(value = "SELECT ", nativeQuery = true)
	// User findUsernameByToken(String token);

	List<User> findByName(String name);

	@Query("select email from User where email = ?1")
	User findByEmail(String email);

	@Query("Select u from User u")
	Page<User> findUser(Pageable pageable);
}
