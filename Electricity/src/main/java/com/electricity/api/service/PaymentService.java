package com.electricity.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.electricity.api.data.BillRepository;

import com.electricity.api.data.PaymentRepository;
import com.electricity.api.exception.PaymentNotFoundException;
import com.electricity.api.model.Bill;
import com.electricity.api.model.Payment;


import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService extends Exception{

	@Autowired

	private PaymentRepository paymentRepository;

	@Autowired

	private BillRepository billRepository;
	

	public double latePaidAmount(Payment payment) {

		double finePerDay = 13;

		long days = ChronoUnit.DAYS.between(payment.getPaymentDate(), payment.getBill().getDueDate());

		double fineAmount = Math.abs(days * finePerDay);

		return fineAmount;

	}

	
	//Post Mapping
	public void assign(Payment payment) {
	    payment.setTotalAmount(payment.getBill().getAmount() + latePaidAmount(payment));
	    
	    // Generate transaction ID
	    String transactionId = generateTransactionId();
	    payment.setTransactionId(transactionId);
	    
	    paymentRepository.save(payment);
	}

	private String generateTransactionId() {
	    // Define the length of the transaction ID
	    int length = 10;
	    
	    // Define the characters allowed in the transaction ID
	    String allowedCharacters = "0123456789";
	    
	    Random random = new Random();
	    StringBuilder transactionIdBuilder = new StringBuilder(length);
	    
	    // Generate random digits for the transaction ID
	    for (int i = 0; i < length; i++) {
	        int randomIndex = random.nextInt(allowedCharacters.length());
	        char randomChar = allowedCharacters.charAt(randomIndex);
	        transactionIdBuilder.append(randomChar);
	    }
	    
	    return transactionIdBuilder.toString();
	}

	
	//GetAll
	public List<Payment> getAllPaymentRecords() {
		// TODO Auto-generated method stub
		return paymentRepository.findAll();
	}

	
	//Get By Payment Id
	public Optional<Payment> getPaymentById(int id) throws PaymentNotFoundException{
		// TODO Auto-generated method stub
		Optional<Payment> payment = paymentRepository.findById(id);
		if (!payment.isPresent()) {
			throw new PaymentNotFoundException("Payment not found with ID: " + id);
		}
		return payment;
	}
	
	
	//Delete Mapping
		public void deletePaymentById(int id) throws PaymentNotFoundException{
			Optional<Payment> payment = paymentRepository.findById(id);
			if (!payment.isPresent()) {
				throw new PaymentNotFoundException("Payment not found with ID: " + id);
			}
			paymentRepository.deleteById(id);
		}

		
	// Get payment by customer ID
	public List<Payment> getPaymentByCustomerId(int cid) throws PaymentNotFoundException{
		// Fetch all payments from the DB
		List<Payment> list = paymentRepository.findAll();

		List<Payment> filteredList = list.stream().filter(e -> e.getCustomer().getId() == cid)
				.collect(Collectors.toList());
		
		return filteredList;
	}
	

}

