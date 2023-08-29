package com.electricity.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electricity.api.model.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	

}
