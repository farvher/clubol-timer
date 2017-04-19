package org.clubol.dao;

import java.util.List;

import org.clubol.entity.Race;
import org.clubol.entity.Runner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceDao extends JpaRepository<Race, Long>  {

	
	Race findFirstByRaceName(String raceName);
	
}
