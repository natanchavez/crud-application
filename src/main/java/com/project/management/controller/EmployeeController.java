package com.project.management.controller;

import com.project.management.exception.EmployeeNotFoundException;
import com.project.management.model.Employee;
import com.project.management.model.JobTitle;
import com.project.management.service.EmployeeService;
import com.project.management.service.JobTitleService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller - incoming HTTP requests made by the user are handled by this class.
 */
@Controller
public class EmployeeController {
	
	final EmployeeService employeeService;
	final JobTitleService jobTitleService;
	
	public EmployeeController(EmployeeService employeeService, JobTitleService jobTitleService) {
		this.employeeService = employeeService;
		this.jobTitleService = jobTitleService;
	}
	
	/**
	 * Redirects to the home page.
	 *
	 * @return redirects to the home page.
	 */
	@GetMapping(value = "/")
	public String sendToHomePage() {
		return "redirect:/employees";
	}
	
	/**
	 * This method is called through an HTTP GET request, a list is retrieved with all the employees, then that list is
	 * sent to the front-end along with some other useful information regarding the number of employees existing in the
	 * database.
	 *
	 * @param search  A string of text to be searched into the database.
	 * @param show    The number of results to show per page.
	 * @param sort    The field and direction to sort all entries.
	 * @param page    The number of the page to show.
	 * @param model   The model to which certain attributes are going to be added.
	 * @param request Information regarding the HTTP request.
	 * @return The homepage of this application.
	 */
	@GetMapping(value = "/employees")
	public String getAllEmployees(@RequestParam(required = false) String search,
								  @RequestParam(defaultValue = "10") int show,
								  @RequestParam(defaultValue = "employee_number,asc") String[] sort,
								  @RequestParam(defaultValue = "1") int page,
								  Model model, HttpServletRequest request) {
		
		Page<Employee> pageEmployees = employeeService.getAllEmployees(search, page, show, sort);
		
		long numResultsTotal = pageEmployees.getTotalElements();
		int numEmployeesInPage = pageEmployees.getNumberOfElements();
		
		if (numEmployeesInPage == 0 && page > 1) {
			String urlPageWithResults = employeeService.noEmptyPageAfterDelete(request, page);
			
			return "redirect:" + urlPageWithResults;
		}
		
		List<JobTitle> jobTitles = jobTitleService.getAllTitles();
		
		model.addAttribute("jobTitles", jobTitles);
		model.addAttribute("employees", pageEmployees);
		model.addAttribute("employee", new Employee());
		model.addAttribute("page", page);
		model.addAttribute("showPerPage", show);
		model.addAttribute("numResultsInPage", numEmployeesInPage);
		model.addAttribute("numResultsTotal", numResultsTotal);
		
		return "employees-list";
	}
	
	/**
	 * This method is called through an HTTP POST request, an object is received, and it contains all the necessary
	 * information to add a new employee to the database or it can be an existing employee in which case his or her
	 * information is going to be updated.
	 *
	 * @param employeeNumber  The employee number, only if the user is trying to edit an existing employee.
	 * @param employeeNewInfo All the information necessary to add or edit an employee on the database.
	 * @param request         Information regarding the HTTP request.
	 * @return Redirects to the referer URL.
	 * @throws EmployeeNotFoundException Exception thrown if the employee number to be edited doesn’t exist.
	 */
	@PostMapping(value = "/employees/add-edit")
	public String addOrEditEmployee(@RequestParam(name = "employee_number", required = false) Long employeeNumber,
									@ModelAttribute Employee employeeNewInfo,
									HttpServletRequest request) throws EmployeeNotFoundException {
		
		employeeService.addOrEditEmployee(employeeNumber, employeeNewInfo);
		
		String referrerURL = request.getHeader("Referer");
		return "redirect:" + referrerURL;
	}
	
	/**
	 * This method is called through an HTTP POST request, an employee number is received and used to find and delete a
	 * specific entry on the database.
	 *
	 * @param employeeNumber The number of the employee that the user is trying to delete.
	 * @param request        Information regarding the HTTP request.
	 * @return Redirects to the referer URL.
	 * @throws EmployeeNotFoundException Exception thrown if the employee number to be deleted doesn’t exist.
	 */
	@PostMapping(value = "/employees/delete")
	public String deleteEmployee(@RequestParam(name = "employee_number") Long employeeNumber,
								 HttpServletRequest request) throws EmployeeNotFoundException {
		
		employeeService.deleteEmployee(employeeNumber);
		
		String referrerURL = request.getHeader("Referer");
		return "redirect:" + referrerURL;
	}
	
	/**
	 * This method is called through an HTTP GET request, receives the number of a specific employee and finds it on the
	 * database if that employee exists.
	 *
	 * @param employeeNumber The number of the employee that the user is trying to find.
	 * @return An object with all the information regarding a certain employee.
	 * @throws EmployeeNotFoundException Exception thrown if the employee number to be found doesn’t exist.
	 */
	@ResponseBody
	@GetMapping(value = "/employees/find-employee/{id}")
	public Employee findAnEmployee(@PathVariable(name = "id") Long employeeNumber) throws EmployeeNotFoundException {
		return employeeService.findEmployee(employeeNumber);
	}
	
	/**
	 * The 'about' page is sent to the user.
	 *
	 * @return The 'about' page.
	 */
	@RequestMapping("/about")
	public String aboutPage() {
		return "about";
	}
}