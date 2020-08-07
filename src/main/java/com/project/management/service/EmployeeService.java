package com.project.management.service;

import com.project.management.exception.EmployeeNotFoundException;
import com.project.management.model.Employee;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService {
	
	Page<Employee> getAllEmployees(String search, int page, int show, String[] sort);
	
	void deleteEmployee(Long employeeNumber) throws EmployeeNotFoundException;
	
	void addOrEditEmployee(Long employeeNumber, Employee employeeNewInfo) throws EmployeeNotFoundException;
	
	Employee findEmployee(Long employeeNumber) throws EmployeeNotFoundException;
	
	String noEmptyPageAfterDelete(HttpServletRequest request, int page);
}
