package org.clubol.services;

import java.util.List;

import org.clubol.dao.RaceDao;
import org.clubol.entity.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaceServiceImpl implements RaceService{

	@Autowired
	private RaceDao racedao;
	
	@Autowired
	private ChronometerService chronometerService;
	
	@Override
	public void saveRace(Race race) {
		race.setChronometers(chronometerService.findAll());
		racedao.save(race);
	}

	@Override
	public Race findByRaceName(String raceName) {
		return racedao.findFirstByRaceName(raceName);
	}

	@Override
	public List<Race> findAll() {
		return  racedao.findAll();
	}

}
