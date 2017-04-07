package org.clubol.services;

import java.util.Date;
import java.util.List;

import org.clubol.dao.TagsRepository;
import org.clubol.entity.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


 @Service
public class TagsServiceImpl  implements TagsService{

	 @Autowired
	private TagsRepository tagRepository;
	

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
	
	
	

}
