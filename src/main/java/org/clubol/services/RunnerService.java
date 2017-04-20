package org.clubol.services;

import java.util.List;

import org.clubol.entity.Runner;

public interface RunnerService {
	
	List<Runner> findByLastName(String lastName);
	
	void saveRunner(Runner runner);
	
	List<Runner> findAll();

	List<Runner> findByDocumentOrFirstNameOrLastName(String document,String firtsName, String lastName);

	Runner findByPosition(Long position);
	
	Runner findById(Long id);
	
	Runner findFirstByDocument(String document);
	
	
	void disableRunner(Long id);

	void delete(Long id);	
	
	
	List<Object[]> findDuplicatePositions();

	List<Runner> findByPositionAll(Long id);
	

}
