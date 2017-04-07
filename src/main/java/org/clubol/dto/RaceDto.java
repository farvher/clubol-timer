package org.clubol.dto;

import java.io.Serializable;

import org.clubol.entity.Runner;

public class RaceDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String document;
	
	private String firstName;
	
	private String lastName;
	
	private String time;
	
	private String gunTime;
	
	private String category;
	
	private String position;

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getGunTime() {
		return gunTime;
	}

	public void setGunTime(String gunTime) {
		this.gunTime = gunTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public static RaceDto EntityToRace(Runner runner){
		
		RaceDto raceDto = new RaceDto();
		raceDto.setDocument(runner.getDocument());
		raceDto.setCategory(runner.getCategory().getName());
		raceDto.setFirstName(runner.getFirstName());
		raceDto.setLastName(runner.getLastName());
		
		
		return raceDto;
	}
	
	
	
	

}
