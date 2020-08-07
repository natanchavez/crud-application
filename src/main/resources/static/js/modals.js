/**
 * This function is going to be executed only if the page has been loaded completely.
 */
$(document).ready(function () {
	
	/**
	 * When the user tries to edit an employee an asynchronous get request is going to be made in order to retrieve all
	 * the information for a specific employee, then that information is used to populate a form so the user only has
	 * to modify the existing data for an employee.
	 */
	$(".edit-modal-btn").on("click", function (event) {
		event.preventDefault();
		
		let href = $(this).attr('href');
		
		$.get(href, function (employee) {
			let title = employee.title;
			
			$("#employee-number-edit").val(employee.employee_number);
			$("#employee-lastname-edit").val(employee.employee_lastname);
			$("#employee-name-edit").val(employee.employee_name);
			$("#joining-date-edit").val(employee.joining_date);
			$(`#title-edit option[value='${title}']`).prop('selected', true);
		});
		
		$("#edit-title-text").text("Edit employee");
		$("#edit-user-modal").modal();
	})
	
	/**
	 * When the user is trying to add a new employee then this function reuses the modal for editing and cleans all the
	 * input fields.
	 */
	$("#add-user-btn").on("click", function () {
		$("#edit-user-form").trigger("reset");
		$(`#title-edit option[value='']`).prop('selected', true);
		$("#edit-title-text").text("Add new employee");
		
		$("#edit-user-modal").modal();
	})
	
	/**
	 * This function retrieves the number of the employee that the user is trying to delete, then that value is set
	 * onto a field inside the modal in order to make an HTTP request to delete that employee.
	 */
	$(".delete-modal-btn").on("click", function () {
		let employeeNumber = $(this).next(".delete-hidden-id").val()
		
		$("#employee-number-delete").val(employeeNumber);
		$("#delete-user-modal").modal();
	})
})