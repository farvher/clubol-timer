package org.clubol.services;

import java.util.List;

import org.clubol.entity.Race;

public interface RaceService {

	
	void saveRace(Race race);
	
	List<Race> findByRaceName(String raceName);
	
	List<Race> findAll();
}
