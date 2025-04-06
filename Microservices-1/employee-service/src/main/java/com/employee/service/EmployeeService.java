package com.employee.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepo;
import com.employee.response.AddressResponse;
import com.employee.response.EmployeeResponse;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	private RestTemplate restTemplate;

	@Autowired
	private WebClient webClient;

//	@Value("${address.base.url}")
//	private String addressBaseUrl;

//	public EmployeeService(@Value("${address.base.url}") String addressBaseUrl, RestTemplateBuilder builder) {
//		System.out.println(addressBaseUrl);
//		this.restTemplate = builder.rootUri(addressBaseUrl).build();
//	}

	public EmployeeResponse getByEmployeeId(int id) {
		Employee employee = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

		EmployeeResponse resp = new EmployeeResponse();

//		AddressResponse address = restTemplate.getForObject("/address/{id}", AddressResponse.class, id);

		AddressResponse address = webClient.get().uri("/address/" + id).retrieve().bodyToMono(AddressResponse.class)
				.block();
		resp.setAddressResponse(address);

		BeanUtils.copyProperties(employee, resp);

		return resp;
	}
}
