package org.clubol.services;

import java.util.List;

import org.clubol.entity.Distance;

public interface DistanceService {
	
	
	void addDistance(Distance distance);
	
	List<Distance> getDistances();
		
		

}
