package com.address.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.address.addressrepo.AddressRepo;
import com.address.addressresponse.AddressResponse;
import com.address.model.Address;

@Service
public class AddressService {

	@Autowired
	private AddressRepo addressRepo;

	public AddressResponse getByAddressId(int id) {
		AddressResponse addressResponse = new AddressResponse();

		Address address = addressRepo.findAddressByEmpId(id);

		BeanUtils.copyProperties(address, addressResponse);

		return addressResponse;

	}

}
