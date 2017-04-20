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
		return runnerRepository.findAll();
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
	public void disableRunner(Long id) {
		Runner r  = runnerRepository.getOne(id);;
		r.setActive(!r.isActive());
		runnerRepository.save(r);
		
	}

	@Override
	public void delete(Long id) {
		runnerRepository.delete(id);
		
	}

}
