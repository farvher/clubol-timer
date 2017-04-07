package org.clubol.services;

import java.util.List;

import org.clubol.dao.TagsRepository;
import org.clubol.entity.Tags;
import org.springframework.stereotype.Service;


 @Service
public class TagsServiceImpl  implements TagsService{

	private TagsRepository tagRepository;
	
	@Override
	public List<Tags> findByNoTag(String noTag) {
		return tagRepository.findAll();
	}

	@Override
	public void saveTag(Tags tag) {
		tagRepository.save(tag);
		
	}

	@Override
	public List<Tags> findAll() {
		return tagRepository.findAll();
	}
	
	
	

}
