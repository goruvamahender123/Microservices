package com.employee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "employee")
@Data
public class Employee {
	
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name ="id")
	@Id
	private int id;
	
	@Column(name ="name")
	private String name;
	
	@Column(name ="bloodgroup")
	private String bloodGroup;
	
	@Column(name ="email")
	private String email;


}
