package com.api_ctv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api_ctv.entities.Cluster;
import com.api_ctv.model.Item;
import com.api_ctv.repo.ClusterRepository;

@Service
public class ClusterServiceImpl implements ClusterService {

	@Autowired
	ClusterRepository clusterRepository;
	
	@Override
	public List<Cluster> findSpecifyCentroid(String with_uindex) {
		return clusterRepository.findSpecifyCentroid(with_uindex);
	}

	@Override
	public List<String> findDistinctItem() {
		return clusterRepository.findDistinctItem();
	}

	@Override
	public Cluster findWithUindexFromUindex(String uindex) {
		return clusterRepository.findWithUindexFromUindex(uindex);
	}
	
	@Override
	public List<Cluster> GetAll_Cluster(){
		return clusterRepository.GetAll_Cluster();
	}
}
