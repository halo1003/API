package com.api_ctv.service;

import com.api_ctv.entities.DistinctUid;

public interface DistinctUidService {
	
	DistinctUid getNumberbyUID(String uid);
	
	int UserIsExist(String uid);
}
