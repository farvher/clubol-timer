package org.clubol.services;

import java.util.List;

import org.clubol.entity.Tags;

public interface TagsService {
	
	List<Tags> findByNoTag(String noTag);
	
	void saveTag(Tags runner);
	
	List<Tags> findAll();

}
