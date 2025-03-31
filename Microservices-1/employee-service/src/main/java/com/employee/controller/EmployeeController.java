package com.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	
	@GetMapping("/employees/{id}")
	public Employee getEmployee(@PathVariable int id) {
		Employee employee = employeeService.getByEmployeeId(id);
		return employee;
	}

}
