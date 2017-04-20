package org.clubol.dao;

import java.util.List;

import org.clubol.entity.Runner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RunnerRepository extends JpaRepository<Runner, Long> {

	List<Runner> findByLastName(String lastName);

	List<Runner> findByDocumentOrFirstNameOrLastName(String document,String firtsName, String lastName);

	Runner findFirstByPosition(Long position);
	
	List<Runner> findByPosition(Long position);
	
	List<Runner> findAllByOrderByPosition();
	
	Runner findFirstByDocument(String document);
	
	
	
	@Query(value = "SELECT POSITION,COUNT(*)"
			+ "FROM runner  "
			+ "GROUP BY POSITION "
			+ "HAVING COUNT(*)>1  "
			+ "ORDER BY POSITION DESC;", nativeQuery = true)
	List<Object[]> findDuplicatePositions();

}
