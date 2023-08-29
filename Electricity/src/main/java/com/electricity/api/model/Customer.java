package com.electricity.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="cust_name")
	private String name;
	
	@Column(name="cust_contactNo")
	private String contactNo;
	
	@Column(name="cust_email")
	private String email;
	
	@Column(name="cust_address")
	private String address;
	
	@OneToOne
	private Meter meter;
	
	@OneToOne
	private User user;
	
	
	
	
	
	
	

}
