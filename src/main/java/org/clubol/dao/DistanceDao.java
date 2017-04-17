package org.clubol.dao;

import org.clubol.entity.Distance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistanceDao extends JpaRepository<Distance,Long>{
	

}
