package org.clubol.dao;


import java.util.Date;

import java.util.List;

import org.clubol.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TagsRepository extends JpaRepository<Tags,Long>{
	
	List<Tags> findByNoTag(Long noTag);
	
	List<Tags> findByNoAerial(Long noAerial);
	
	List<Tags> findByRunDate(String runDate);

}
