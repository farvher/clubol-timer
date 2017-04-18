package org.clubol.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.clubol.dao.RaceDao;
import org.clubol.dao.RunnerRepository;
import org.clubol.dao.TagsRepository;
import org.clubol.dto.RaceDto;
import org.clubol.entity.Race;
import org.clubol.entity.Runner;
import org.clubol.entity.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagsServiceImpl implements TagsService {

	@Autowired
	private TagsRepository tagRepository;

	@Autowired
	private RunnerRepository runnerRepository;
	
	
	@Autowired
	private RaceDao raceRepository;

	@Override
	public void saveTag(Tags tag) {
		tagRepository.save(tag);

	}

	@Override
	public List<Tags> findAll() {
		return tagRepository.findAll();
	}

	@Override
	public List<Tags> findByNoTag(Long noTag) {
		return tagRepository.findByNoTag(noTag);
	}

	@Override
	public List<Tags> findByNoAerial(Long noAerial) {
		return tagRepository.findByNoAerial(noAerial);
	}

	@Override
	public List<Tags> findByNoRunDate(String runDate) {
		return tagRepository.findByRunDate(runDate);
	}

	@Override
	public List<RaceDto> findByRace(String raceName) {
		List<Tags> tags = new ArrayList<>();
		List<Race> races = raceRepository.findByRaceName(raceName);
		if(!races.isEmpty()){
			Race race = races.get(0);
			tags = tagRepository.findByRunDate(race.getDateRace());
		}
		
		return getRaceDto(tags);
	}
	
	private List<RaceDto> getRaceDto(List<Tags> tags){
		List<RaceDto> raceDtoList= new ArrayList<>();
		if(tags.size()>0){
			for(Tags t : tags){
				Runner runner = runnerRepository.findByPosition(t.getNoTag());
				if(runner!=null){
					RaceDto r = new RaceDto();
					r.setCategory(runner.getCategory());
					r.setDistance(runner.getDistance());
					r.setFirstName(runner.getFirstName());
					r.setGender(runner.getGender());
					r.setLastName(runner.getLastName());
					r.setPosition(runner.getPosition().toString());
					r.setTime(t.getTime());
					raceDtoList.add(r);
				}
			}
		}
		return raceDtoList;
	}

}
