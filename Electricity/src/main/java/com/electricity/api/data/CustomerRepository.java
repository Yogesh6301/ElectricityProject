package com.electricity.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electricity.api.model.Customer;



public interface CustomerRepository extends JpaRepository<Customer,Integer>{

}
