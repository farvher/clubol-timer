package org.clubol.services;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
	
	@Override
	public List<RaceDto> findByRaceAndTag(String raceName, Long Tag) {
		List<Tags> tags = new ArrayList<>();
		Race race = raceRepository.findFirstByRaceName(raceName);
		if (race != null) {
			tags = tagRepository.findByNoTag(Tag);
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
					r.setId(t.getId());
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
				} else {
					RaceDto r = new RaceDto();
					r.setId(t.getId());
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

		Chronometer cr = chronometerService.findFirstByChronometerName(category);
		if (cr != null) {
			return getDiferenceTime(cr.getTimeStart(), time);
		}
		return "Categoria no encontrada";
	}

	private String getDiferenceTime(String firsTime, String lastTime) {
		Date firstTimeDate = parseDate(firsTime);
		Date lastTimeDate = parseDate(lastTime);

		if (firstTimeDate.before(lastTimeDate)) {
			Long seconds = (lastTimeDate.getTime() - firstTimeDate.getTime()) / 1000;
			return convertSecondsToHHMMSS(seconds).toString();
		} else {
			return "Mayor que Cronometro";
		}
	}

	private String convertSecondsToHHMMSS(long seconds) {
		Long hr = seconds / 3600;
		Long rem = seconds % 3600;
		Long mn = rem / 60;
		Long sec = rem % 60;
		String hrStr = (hr < 10 ? "0" : "") + hr;
		String mnStr = (mn < 10 ? "0" : "") + mn;
		String secStr = (sec < 10 ? "0" : "") + sec;
		return hrStr + ":" + mnStr + ":" + secStr;

	}

	@Override
	public List<RaceDto> findRunnerAndTags(List<Runner> runners) {
		List<RaceDto> tagRunner = new ArrayList<>();
		for (Runner runner : runners) {
			if (runner.isActive()) {
				List<Tags> listTags = tagRepository.findByNoTag(runner.getPosition());
				Chronometer chronometer = chronometerService.findFirstByChronometerName(runner.getDistance());
				RaceDto r = new RaceDto();
				r.setCategory(runner.getCategory());
				r.setDistance(runner.getDistance());
				r.setFirstName(runner.getFirstName());
				r.setGender(runner.getGender());
				r.setLastName(runner.getLastName());
				r.setPosition(runner.getPosition().toString());
				r.setActive(runner.isActive());
				r.setTimeTags(processTimeTags(listTags));
//				r.setEmail(runner.getEmail());

				if (chronometer != null ) {
					r.setDistanceTime(chronometer.getTimeStart());
					r.setBestTime(processBestime(r.getTimeTags(), chronometer.getTimeStart()));
				}
				if (!r.getTimeTags().isEmpty()) {
					tagRunner.add(r);
				}
			}
		}
		sortByBestTime(tagRunner);
		return tagRunner;
	}

	private List<String> processTimeTags(List<Tags> tags) {
		List<String> tagsProcessed = new ArrayList<>();
		List<Date> unOrderDates = new ArrayList<>();
		for (Tags t : tags) {
			Date d = parseDate(t.getTime());
			unOrderDates.add(d);
		}
		Collections.sort(unOrderDates);

		for (Date d : unOrderDates) {
			String strTime = sdf.format(d);
			tagsProcessed.add(strTime);
		}

		return tagsProcessed;
	}

	private String processBestime(List<String> tagstimes, String categoryTime) {

		if (tagstimes.size() > 1) {
			String firstTime = categoryTime;
			String lastTime = tagstimes.get(tagstimes.size() - 1);

			return getDiferenceTime(categoryTime, lastTime);
		}

		return "No Results";
	}
	
	private String processChiptime(List<String> tagstimes) {

		if (tagstimes.size() > 1) {
			String firstTime = tagstimes.get(0);
			String lastTime = tagstimes.get(tagstimes.size() - 1);

			return getDiferenceTime(firstTime, lastTime);
		}

		return "No Results";
	}

	private void sortByBestTime(List<RaceDto> raceList) {

		Collections.sort(raceList, new Comparator<RaceDto>() {
			@Override
			public int compare(RaceDto o1, RaceDto o2) {
				Date date1 = parseDate(o1.getBestTime());
				Date date2 = parseDate(o2.getBestTime());
				return date1.compareTo(date2);
			}
		});
	}

	private static Date parseDate(String strDate) {
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
		}
		return new Date();
	}

	@Override
	public void deleteTag(Long id) {
		tagRepository.deleteById(id);

	}

	@Override
	public List<RaceDto> findRunnerAndTagsEmail(List<Runner> runers) {
		List<RaceDto> tagRunner = new ArrayList<>();
		for (Runner runner : runers) {
			if (runner.isActive()) {
				List<Tags> listTags = tagRepository.findByNoTag(runner.getPosition());
				Chronometer chronometer = chronometerService.findFirstByChronometerName(runner.getDistance());
				RaceDto r = new RaceDto();
				r.setCategory(runner.getCategory());
				r.setDistance(runner.getDistance());
				r.setFirstName(runner.getFirstName());
//				r.setGender(runner.getGender());
//				r.setLastName(runner.getLastName());
				r.setPosition(runner.getPosition().toString());
//				r.setActive(runner.isActive());
				r.setTimeTags(processTimeTags(listTags));
				r.setPassTime(processChiptime(r.getTimeTags()));
				
				r.setEmail(runner.getEmail());

				if (chronometer != null ) {
					r.setDistanceTime(chronometer.getTimeStart());
					r.setBestTime(processBestime(r.getTimeTags(), chronometer.getTimeStart()));
				}
				if (!r.getTimeTags().isEmpty()) {
					tagRunner.add(r);
				}
			}
		}
		//sortByBestTime(tagRunner);
		return tagRunner;
	}

	

}
