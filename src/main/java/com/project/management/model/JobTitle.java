package com.project.management.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * The job title entity, a table is going to be created on the database using the properties of this object.
 */
@Entity
public class JobTitle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String job_title;
	
	public JobTitle() {
	}
	
	public JobTitle(Long id, @NotBlank String job_title) {
		this.id = id;
		this.job_title = job_title;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getJob_title() {
		return job_title;
	}
	
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	
	@Override
	public String toString() {
		return "JobTitle{" +
				"id=" + id +
				", job_title='" + job_title + '\'' +
				'}';
	}
}
