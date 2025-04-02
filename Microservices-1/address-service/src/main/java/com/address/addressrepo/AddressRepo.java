package com.address.addressrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.address.model.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer>{
	
	@Query(nativeQuery = true,value = "SELECT ad.id, ad.lane1, ad.lane2, ad.lane3, ad.zip FROM mahender.address ad JOIN mahender.employee emp ON emp.id = ad.employeeid where ad.employeeid=:employeeID")
	Address findAddressByEmpId(@Param("employeeID") int id);

}
