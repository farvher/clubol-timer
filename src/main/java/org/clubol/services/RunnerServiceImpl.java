package org.clubol.services;

import java.util.ArrayList;
import java.util.List;

import org.clubol.dao.RunnerRepository;
import org.clubol.entity.Chronometer;
import org.clubol.entity.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunnerServiceImpl  implements RunnerService{

	
	@Autowired
	RunnerRepository runnerRepository;
	
	
	@Override
	public List<Runner> findByLastName(String lastName) {
		return runnerRepository.findByLastName(lastName);
	}

	@Override
	public void saveRunner(Runner runner) {
		runnerRepository.save(runner);
	}

	@Override
	public List<Runner> findAll() {
		return runnerRepository.findAllByOrderByPosition();
	}

	@Override
	public List<Runner> findByDocumentOrFirstNameOrLastName(String document, String firtsName, String lastName) {
		return runnerRepository.findByDocumentOrFirstNameOrLastName(document, firtsName, lastName);
	}

	@Override
	public Runner findByPosition(Long position) {
		return runnerRepository.findFirstByPosition(position);
	}
	
	@Override
	public List<Runner>findByPositionAll(Long id){
		return runnerRepository.findByPosition(id);
		
	}
	

	@Override
	public void disableRunner(Long id) {
		Runner r  = runnerRepository.getOne(id);;
		r.setActive(!r.isActive());
		runnerRepository.save(r);
		
	}

	@Override
	public void delete(Long id) {
		runnerRepository.delete(id);
		
	}

	@Override
	public Runner findById(Long id) {
		return runnerRepository.getOne(id);
	}

	@Override
	public Runner findFirstByDocument(String document) {
		return  runnerRepository.findFirstByDocument(document);
	}

	@Override
	public List<Object[]> findDuplicatePositions() {
		return runnerRepository.findDuplicatePositions();
	}

}
