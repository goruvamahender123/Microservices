package com.employee.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepo;
import com.employee.response.AddressResponse;
import com.employee.response.EmployeeResponse;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private RestTemplate restTemplate;

	public EmployeeResponse getByEmployeeId(int id) {
		Employee employee = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

		EmployeeResponse resp = new EmployeeResponse();
		
		ResponseEntity<AddressResponse> address = restTemplate.getForEntity("http://localhost:8081/address-app/address/{id}", AddressResponse.class, id);
		resp.setAddressResponse(address.getBody());
		
		BeanUtils.copyProperties(employee, resp);

		return resp;
	}
}
