package com.api_ctv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api_ctv.entities.DistinctUid;
import com.api_ctv.repo.DistinctUidRepository;

@Service
public class DistinctUidImpl implements DistinctUidService{
	
	@Autowired
	DistinctUidRepository changingUserToNumber;

	@Override
	public DistinctUid getNumberbyUID(String uid) {
		return changingUserToNumber.getNumberbyUID(uid);
	}

	@Override
	public int UserIsExist(String uid) {
		return changingUserToNumber.UserIsExist(uid);
	}

}
