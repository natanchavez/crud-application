package com.project.management.repository;

import com.project.management.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository - This class provides the mechanism to perform all the CRUD operation on the employee object.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	/**
	 * This method uses a query to concatenate all the fields on every entry of the database in order to search and find
	 * all elements containing a certain keyword.
	 *
	 * @param search   The keyword to be found.
	 * @param pageable An object that contains all the existing employees.
	 * @return An object that contains all the employees whose information contains the specified keyword.
	 */
	@Query("SELECT p FROM Employee p WHERE lower(CONCAT(" +
			"p.employee_number, ' ', p.employee_name, ' ', p.employee_lastname, ' ', p.title, ' ', p.joining_date)) " +
			"LIKE %?1%")
	Page<Employee> findAllContaining(String search, Pageable pageable);
	
	/**
	 * This method uses a query to retrieve all employees on the employee table.
	 *
	 * @param pageable An object that contains all the existing employees.
	 * @return An object that contains all the existing employees on the employee table.
	 */
	@Query("SELECT p FROM Employee p")
	Page<Employee> findAll(Pageable pageable);
}