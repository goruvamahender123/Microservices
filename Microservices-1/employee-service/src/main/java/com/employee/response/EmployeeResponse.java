package com.employee.response;

import lombok.Data;

@Data
public class EmployeeResponse {

	private int id;
	private String bloodGroup;
	private String name;
	private String email;
	private AddressResponse addressResponse;
}
