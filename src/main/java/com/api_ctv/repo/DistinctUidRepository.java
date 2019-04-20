package com.api_ctv.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api_ctv.entities.DistinctUid;

@Repository
public interface DistinctUidRepository extends JpaRepository<DistinctUid, Integer> {
	
	@Query(value = "SELECT * FROM distinct_uid d WHERE d.uid = :uid", nativeQuery = true)
	DistinctUid getNumberbyUID(@Param("uid") String uid);
	
	@Query(value = "SELECT Count(*) FROM distinct_uid d WHERE d.uid = :uid", nativeQuery = true)
	int UserIsExist(@Param("uid") String uid);
}
