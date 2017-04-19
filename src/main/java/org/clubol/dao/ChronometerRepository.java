package org.clubol.dao;


import org.clubol.entity.Chronometer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChronometerRepository  extends JpaRepository<Chronometer,Long>{

	Chronometer findFirstByChronometerName(String chronometerName);
	
	
}
