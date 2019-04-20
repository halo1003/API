package com.api_ctv.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api_ctv.entities.DistinctUid;
import com.api_ctv.entities.Music;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {
	
	@Query(value = "SELECT * FROM music m WHERE m.musicid = :mid LIMIT 1", nativeQuery = true)
	Music getTrackIdbyMID(@Param("mid") String mid);
	
	@Query(value = "SELECT * FROM music m WHERE m.trackid = :trackid LIMIT 1", nativeQuery = true)
	Music getMIDbyTrackId(@Param("trackid") String trackid);
}
