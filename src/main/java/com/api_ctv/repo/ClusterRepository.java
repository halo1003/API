package com.api_ctv.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api_ctv.entities.Cluster;
import com.api_ctv.model.Item;
import com.api_ctv.model.UserHistory;

@Repository
public interface ClusterRepository extends JpaRepository<Cluster, Integer> {
	
	@Query("SELECT c FROM Cluster c WHERE c.with_uindex = :ui")
	List<Cluster> findSpecifyCentroid(@Param("ui") String withUindex);
	
	@Query(value = "SELECT DISTINCT c.with_uindex FROM clustering c", nativeQuery = true)
	List<String> findDistinctItem();
	
	@Query(value = "SELECT * FROM clustering WHERE uindex = :uindex", nativeQuery = true)
	Cluster findWithUindexFromUindex(@Param("uindex") String uindex);
	
	@Query(value = "SELECT * FROM clustering", nativeQuery = true)
	List<Cluster> GetAll_Cluster();
}
