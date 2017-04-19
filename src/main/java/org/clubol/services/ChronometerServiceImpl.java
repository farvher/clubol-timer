package org.clubol.services;

import java.util.List;

import org.clubol.dao.ChronometerRepository;
import org.clubol.entity.Chronometer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChronometerServiceImpl implements ChronometerService {

	
	@Autowired
	private ChronometerRepository chronometerRepository;
	
	@Override
	public void update(Chronometer chronometer) {
		chronometerRepository.save(chronometer);
		
	}

	@Override
	public void save(Chronometer chronometer) {
		chronometerRepository.save(chronometer);
		
	}

	@Override
	public Chronometer findById(Long id) {
		return chronometerRepository.getOne(id);
	}

	@Override
	public List<Chronometer> findAll() {
		return chronometerRepository.findAll();
	}

	@Override
	public Chronometer findFirstByChronometerName(String chronometerName) {
		return chronometerRepository.findFirstByChronometerName(chronometerName);
	}

}
