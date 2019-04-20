package com.api_ctv.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.api_ctv.entities.Cluster;
import com.api_ctv.entities.Music;
import com.api_ctv.model.Item;

public interface MusicService {
	
	Music getTrackIdbyMID(String mid);
	
	Music getMIDbyTrackId(String trackid);
}
