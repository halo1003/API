package com.api_ctv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api_ctv.entities.Music;
import com.api_ctv.repo.MusicRepository;

@Service
public class MusicServiceImpl implements MusicService{
	
	@Autowired
	MusicRepository musicRepository;

	@Override
	public Music getTrackIdbyMID(String mid) {
		// TODO Auto-generated method stub
		return musicRepository.getTrackIdbyMID(mid);
	}

	@Override
	public Music getMIDbyTrackId(String trackid) {
		// TODO Auto-generated method stub
		return musicRepository.getMIDbyTrackId(trackid);
	}
	
}
