package com.address.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.address.addressresponse.AddressResponse;
import com.address.service.AddressService;

@RestController
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping("/address/{employeeid}")
	public ResponseEntity<AddressResponse> getAddress(@PathVariable("employeeid") int id) {
		AddressResponse addressResponse = addressService.getByAddressId(id);
		return ResponseEntity.status(HttpStatus.OK).body(addressResponse);
	}

}
