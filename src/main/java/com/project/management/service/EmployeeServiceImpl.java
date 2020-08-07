package com.project.management.service;

import com.project.management.exception.EmployeeNotFoundException;
import com.project.management.model.Employee;
import com.project.management.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Service - This class contains the business logic needed to perform most operations used on this application.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	final EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	/**
	 * Creates a page object, that is used to properly display the information into a table.
	 *
	 * @param search A string of text to be searched into the database.
	 * @param page   The number of the page to show.
	 * @param show   The number of results to show per page.
	 * @param sort   The field and direction to sort all entries.
	 * @return A list with all the employees or a list with all the employees that contain a certain keyword.
	 */
	@Override
	public Page<Employee> getAllEmployees(String search, int page, int show, String[] sort) {
		List<Sort.Order> orders = new ArrayList<>();
		Page<Employee> pageEmployees;
		Pageable pagingSort;
		Sort.Direction sortDirection;
		String field = sort[0];
		String direction = sort[1];
		Sort.Order sortOrder;
		
		if (direction.equals("desc")) {
			sortDirection = Sort.Direction.DESC;
		} else {
			sortDirection = Sort.Direction.ASC;
		}
		
		sortOrder = new Sort.Order(sortDirection, field);
		orders.add(sortOrder);
		
		pagingSort = PageRequest.of(page - 1, show, Sort.by(orders)); //page-1 -> zero-based index
		
		if (search == null) {
			pageEmployees = employeeRepository.findAll(pagingSort);
		} else {
			pageEmployees = employeeRepository.findAllContaining(search.toLowerCase(), pagingSort);
		}
		
		return pageEmployees;
	}
	
	/**
	 * an object is received, and it contains all the necessary information to add a new employee to the database or it
	 * can be an existing employee in which case his or her information is going to be updated.
	 *
	 * @param employeeNumber  The employee number, only if the user is trying to edit an existing employee.
	 * @param employeeNewInfo All the information necessary to add or edit an employee on the database.
	 * @throws EmployeeNotFoundException Exception thrown if the employee number to be edited doesn’t exist.
	 */
	@Override
	public void addOrEditEmployee(Long employeeNumber, Employee employeeNewInfo) throws EmployeeNotFoundException {
		
		if (employeeNumber == null) {
			employeeRepository.save(employeeNewInfo);
			
		} else {
			Employee editedEmployee = employeeRepository.findById(employeeNumber)
					.orElseThrow(() -> new EmployeeNotFoundException(employeeNumber));
			
			editedEmployee.setEmployee_name(employeeNewInfo.getEmployee_name());
			editedEmployee.setEmployee_lastname(employeeNewInfo.getEmployee_lastname());
			editedEmployee.setTitle(employeeNewInfo.getTitle());
			editedEmployee.setJoining_date(employeeNewInfo.getJoining_date());
			
			employeeRepository.save(editedEmployee);
		}
	}
	
	/**
	 * An employee number is received and used to find and delete a specific entry on the database.
	 *
	 * @param employeeNumber The number of the employee that the user is trying to delete.
	 * @throws EmployeeNotFoundException Exception thrown if the employee number to be deleted doesn’t exist.
	 */
	@Override
	public void deleteEmployee(Long employeeNumber) throws EmployeeNotFoundException {
		Employee employee = employeeRepository.findById(employeeNumber)
				.orElseThrow(() -> new EmployeeNotFoundException(employeeNumber));
		
		employeeRepository.delete(employee);
	}
	
	/**
	 * Receives the number of a specific employee and finds it on the database if that employee exists.
	 *
	 * @param employeeNumber The number of the employee that the user is trying to find.
	 * @return An object with all the information regarding a certain employee.
	 * @throws EmployeeNotFoundException Exception thrown if the employee number to be found doesn’t exist.
	 */
	@Override
	public Employee findEmployee(Long employeeNumber) throws EmployeeNotFoundException {
		return employeeRepository.findById(employeeNumber)
				.orElseThrow(() -> new EmployeeNotFoundException(employeeNumber));
	}
	
	/**
	 * This method checks if after an employee has been deleted the current page has no entries, if that is the case the
	 * redirects the user to a previous page with results.
	 *
	 * @param request Information regarding the HTTP request.
	 * @param page    The current number of the page shown to the user.
	 * @return The new number of the page.
	 */
	@Override
	public String noEmptyPageAfterDelete(HttpServletRequest request, int page) {
		String referrerURL = request.getHeader("Referer");
		
		return referrerURL.replaceAll("([?&]page=)([^&]*)", "$1" + (page - 1));
	}
}
