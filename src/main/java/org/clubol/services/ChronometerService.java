package org.clubol.services;

import java.util.List;

import org.clubol.entity.Chronometer;

public interface ChronometerService {

	void update(Chronometer chronometer);

	void save(Chronometer chronometer);

	Chronometer findById(Long id);
	
	List<Chronometer> findAll();
	
	Chronometer findFirstByChronometerName(String chronometerName);

}
