package com.project.management.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * The employee entity, a table is going to be created on the database using the properties of this object.
 */
@Entity
public class Employee {
	@Id
	@TableGenerator(name = "employee_gen", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "employee_gen")
	private Long employee_number;
	@NotBlank
	private String employee_name;
	@NotBlank
	private String employee_lastname;
	@NotBlank
	private String title;
	@NotNull
	private Date joining_date; // "yyyy-MM-dd"
	
	public Employee() {
	}
	
	public Employee(Long employee_number, @NotBlank String employee_name, @NotBlank String employee_lastname,
					@NotBlank String title, Date joining_date) {
		this.employee_number = employee_number;
		this.employee_name = employee_name;
		this.employee_lastname = employee_lastname;
		this.title = title;
		this.joining_date = joining_date;
	}
	
	@Override
	public String toString() {
		return "{" +
				"\"employee_number\":\"" + employee_number + "\"," +
				"\"employee_name\":\"" + employee_name + "\"," +
				"\"employee_lastname\":\"" + employee_lastname + "\"," +
				"\"title\":\"" + title + "\"," +
				"\"joining_date\":\"" + joining_date + "\"" +
				"}";
	}
	
	public Long getEmployee_number() {
		return employee_number;
	}
	
	public void setEmployee_number(Long employee_number) {
		this.employee_number = employee_number;
	}
	
	public String getEmployee_name() {
		return employee_name;
	}
	
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	
	public String getEmployee_lastname() {
		return employee_lastname;
	}
	
	public void setEmployee_lastname(String employee_lastname) {
		this.employee_lastname = employee_lastname;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getJoining_date() {
		return joining_date;
	}
	
	public void setJoining_date(Date joining_date) {
		this.joining_date = joining_date;
	}
}
