package com.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.employee.response.EmployeeResponse;
import com.employee.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employees/{id}")
	public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable int id) {
		EmployeeResponse employeeResponse = employeeService.getByEmployeeId(id);
		return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
	}

}
