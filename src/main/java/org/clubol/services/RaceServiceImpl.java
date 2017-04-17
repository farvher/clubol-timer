package org.clubol.services;

import java.util.ArrayList;
import java.util.List;

import org.clubol.dao.DistanceDao;
import org.clubol.dao.RaceDao;
import org.clubol.entity.Chronometer;
import org.clubol.entity.Distance;
import org.clubol.entity.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaceServiceImpl implements RaceService{

	@Autowired
	private RaceDao racedao;
	
	@Autowired
	
	private DistanceDao distanceDao;
	
	@Override
	public void saveRace(Race race) {
		List<Chronometer> cronometros = new ArrayList<>();
		for(Distance d : distanceDao.findAll()){
			Chronometer c = new Chronometer();
			c.setChronometerName("Cronometro: "+ d.getDistance());
			c.setTimeStart("0:00:00");
			c.setTimeEnd("0:00:00");
			cronometros.add(c);
		}
		race.setChronometers(cronometros);
		racedao.save(race);
	}

	@Override
	public List<Race> findByRaceName(String raceName) {
		return racedao.findByRaceName(raceName);
	}

	@Override
	public List<Race> findAll() {
		return  racedao.findAll();
	}

}
