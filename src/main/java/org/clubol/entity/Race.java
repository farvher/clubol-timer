package org.clubol.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Race {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private String raceName;
	
	private String description;
	
	@OneToMany(targetEntity = Chronometer.class)
	private List<Chronometer> chronometers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRaceName() {
		return raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Chronometer> getChronometers() {
		return chronometers;
	}

	public void setChronometers(List<Chronometer> chronometers) {
		this.chronometers = chronometers;
	}
	
	
	

}
