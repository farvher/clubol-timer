package org.clubol.services;

import java.util.List;

import org.clubol.dao.DistanceDao;
import org.clubol.entity.Distance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistanceServiceImpl implements DistanceService{

	@Autowired
	private DistanceDao distanceDao;
	
	
	@Override
	public void addDistance(Distance distance) {
		distanceDao.save(distance);		
	}

	@Override
	public List<Distance> getDistances() {
		return distanceDao.findAll();
	}

}
