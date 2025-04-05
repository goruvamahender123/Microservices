package com.employee.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

	private RestTemplate restTemplate;

//	@Value("${address.base.url}")
//	private String addressBaseUrl;

	public EmployeeService(@Value("${address.base.url}") String addressBaseUrl, RestTemplateBuilder builder) {
		System.out.println(addressBaseUrl);
		this.restTemplate = builder.rootUri(addressBaseUrl).build();
	}

	public EmployeeResponse getByEmployeeId(int id) {
		Employee employee = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

		EmployeeResponse resp = new EmployeeResponse();

		ResponseEntity<AddressResponse> address = restTemplate.getForEntity("/address/{id}", AddressResponse.class, id);
		resp.setAddressResponse(address.getBody());

		BeanUtils.copyProperties(employee, resp);

		return resp;
	}
}
