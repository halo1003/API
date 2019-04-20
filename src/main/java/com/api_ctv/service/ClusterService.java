package com.api_ctv.service;

import java.util.List;

import com.api_ctv.entities.Cluster;
import com.api_ctv.model.Item;

public interface ClusterService {
	
	List<Cluster> findSpecifyCentroid(String with_uindex);
	
	List<String> findDistinctItem();
	
	Cluster findWithUindexFromUindex(String uindex);
	
	List<Cluster> GetAll_Cluster();	
}
