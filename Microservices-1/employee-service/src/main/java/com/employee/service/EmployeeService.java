package com.employee.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepo;
import com.employee.response.EmployeeResponse;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	public EmployeeResponse getByEmployeeId(int id) {
		Employee employee = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

		EmployeeResponse resp = new EmployeeResponse();
		BeanUtils.copyProperties(employee, resp);

		return resp;
	}
}
