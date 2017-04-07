package org.clubol.services;

import java.util.Date;
import java.util.List;

import org.clubol.entity.Tags;

public interface TagsService {

	void saveTag(Tags runner);

	List<Tags> findAll();

	List<Tags> findByNoTag(Long noTag);

	List<Tags> findByNoAerial(Long noAerial);

	List<Tags> findByNoRunDate(String runDate);

}
