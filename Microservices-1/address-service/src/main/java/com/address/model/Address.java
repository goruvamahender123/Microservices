package com.address.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "address")
public class Address {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "lane1")
	private String lane1;

	@Column(name = "lane2")
	private String lane2;

	@Column(name = "lane3")
	private String lane3;

	@Column(name = "zip")
	private String zip;

//	@Column(name = "employeeid")
//	private String employeeid;
}
