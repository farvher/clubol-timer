package org.clubol.dto;

import java.io.Serializable;
import java.util.List;

import org.clubol.entity.Runner;

public class RaceDto implements Serializable {

	/**
	 * 
	 */
	private Long id;
	
	private static final long serialVersionUID = 1L;

	private String firstName;

	private String lastName;
	
	private String gender;

	private String time;
	
	private String passTime;
	
	private String bestTime;
	
	private List<String> timeTags;

	private String category;

	private String position;
	
	private String distance;
	
	private String distanceTime;
	
	private boolean active;
	
	private String email;

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPassTime() {
		return passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<String> getTimeTags() {
		return timeTags;
	}

	public void setTimeTags(List<String> timeTags) {
		this.timeTags = timeTags;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBestTime() {
		return bestTime;
	}

	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}

	public String getDistanceTime() {
		return distanceTime;
	}

	public void setDistanceTime(String distanceTime) {
		this.distanceTime = distanceTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	
}
