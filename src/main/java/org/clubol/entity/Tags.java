package org.clubol.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Tags {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long noTag;
	
	private String time;
	
	private Long noAerial;
	
	
	private String runDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNoTag() {
		return noTag;
	}

	public void setNoTag(Long noTag) {
		this.noTag = noTag;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getNoAerial() {
		return noAerial;
	}

	public void setNoAerial(Long noAerial) {
		this.noAerial = noAerial;
	}


	public String getRunDate() {
		return runDate;
	}

	public void setRunDate(String runDate) {
		this.runDate = runDate;
	}
	
	

	
	
	
	
	
}
