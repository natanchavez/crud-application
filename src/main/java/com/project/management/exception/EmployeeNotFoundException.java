package com.project.management.exception;

/**
 * This exception is thrown every time the user specifies the number of an employee that doesn’t exist.
 */
public class EmployeeNotFoundException extends Exception {
	
	public EmployeeNotFoundException(long employeeNumber) {
		super(String.format("Employee with number: '%s' was not found.", employeeNumber));
	}
}