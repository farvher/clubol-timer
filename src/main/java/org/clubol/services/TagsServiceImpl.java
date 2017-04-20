package org.clubol.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.clubol.dao.RaceDao;
import org.clubol.dao.RunnerRepository;
import org.clubol.dao.TagsRepository;
import org.clubol.dto.RaceDto;
import org.clubol.entity.Chronometer;
import org.clubol.entity.Race;
import org.clubol.entity.Runner;
import org.clubol.entity.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagsServiceImpl implements TagsService {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss");
	
	private static final String UNKNOW = "desconocido";

	@Autowired
	private TagsRepository tagRepository;

	@Autowired
	private RunnerRepository runnerRepository;

	@Autowired
	private ChronometerService chronometerService;

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
		Race race = raceRepository.findFirstByRaceName(raceName);
		if (race != null) {
			tags = tagRepository.findByRunDate(race.getDateRace());
		}

		return getRaceDto(tags);
	}

	private List<RaceDto> getRaceDto(List<Tags> tags) {
		List<RaceDto> raceDtoList = new ArrayList<>();
		if (tags.size() > 0) {
			for (Tags t : tags) {
				Runner runner = runnerRepository.findFirstByPosition(t.getNoTag());
				if (runner != null) {
					RaceDto r = new RaceDto();
					r.setCategory(runner.getCategory());
					r.setDistance(runner.getDistance());
					r.setFirstName(runner.getFirstName());
					r.setGender(runner.getGender());
					r.setLastName(runner.getLastName());
					r.setPosition(runner.getPosition().toString());
					r.setTime(t.getTime());
					r.setActive(runner.isActive());
					r.setPassTime(this.compareTime(r.getDistance(), r.getTime()));
					raceDtoList.add(r);
				}else {
					RaceDto r = new RaceDto();
					r.setCategory(UNKNOW);
					r.setDistance(UNKNOW);
					r.setFirstName(UNKNOW);
					r.setGender(UNKNOW);
					r.setLastName(UNKNOW);
					r.setPosition(t.getNoTag().toString());
					r.setTime(t.getTime());
					r.setPassTime(UNKNOW);
					raceDtoList.add(r);
					
				}
			}
		}
		return raceDtoList;
	}

	private String compareTime(String category, String time) {

		String timeCr;
		Chronometer cr = chronometerService.findFirstByChronometerName(category);
		try {
			if (cr != null) {
				timeCr = cr.getTimeStart();
				Date crDate = sdf.parse(timeCr);
				Date timeDate = sdf.parse(time);

				if (crDate.before(timeDate)) {
					Long seconds = (timeDate.getTime()-crDate.getTime())/1000 ;
					return convertSecondsToHHMMSS(seconds).toString();
				}
			}
		} catch (Exception ex) {
			return "error";
		}
		return time;
	}
	
	private String convertSecondsToHHMMSS(long seconds){
		  Long hr = seconds/3600;
		  Long rem = seconds%3600;
		  Long mn = rem/60;
		  Long sec = rem%60;
		  String hrStr = (hr<10 ? "0" : "")+hr;
		  String mnStr = (mn<10 ? "0" : "")+mn;
		  String secStr = (sec<10 ? "0" : "")+sec; 
		  return hrStr+":"+mnStr+":"+secStr;
		
	}
	
	private String twoTagsCompare(Long tag){
		List<Tags> tags = tagRepository.findByNoTag(tag);
		
		if(tags.size()>1){
//			Date maxTime =  sdf.parse(tags.get(0).getTime());
//			Date minTime = sdf.parse(tags.get(0).getTime());
			for(Tags t: tags){
			}
			
		}
		return tags.size()+"";
	}

}
