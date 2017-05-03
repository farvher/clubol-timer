package org.clubol.services;

import java.util.Date;
import java.util.List;

import org.clubol.dto.RaceDto;
import org.clubol.entity.Runner;
import org.clubol.entity.Tags;

public interface TagsService {

	void saveTag(Tags runner);

	List<Tags> findAll();

	List<Tags> findByNoTag(Long noTag);

	List<Tags> findByNoAerial(Long noAerial);

	List<Tags> findByNoRunDate(String runDate);

	List<RaceDto> findByRace(String raceName);
	
	List<RaceDto> findByRaceAndTag(String raceName,Long Tag);
	
	List<RaceDto> findRunnerAndTags(List<Runner> runers);
	
	List<RaceDto> findRunnerAndTagsEmail(List<Runner> runers);
	
	void deleteTag(Long id);
	
}
