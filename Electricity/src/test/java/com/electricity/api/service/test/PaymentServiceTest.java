package com.electricity.api.service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.electricity.api.data.BillRepository;
import com.electricity.api.data.PaymentRepository;
import com.electricity.api.exception.PaymentNotFoundException;
import com.electricity.api.model.Bill;
import com.electricity.api.model.Customer;
import com.electricity.api.model.Payment;
import com.electricity.api.service.PaymentService;

public class PaymentServiceTest {
	
	@Mock
	private PaymentRepository paymentRepository;
	
	@Mock
	private BillRepository billRepository;
	
	@InjectMocks
	private PaymentService paymentService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testLatePaidAmount() {
		Payment payment = new Payment();
		Bill bill = new Bill();
		bill.setDueDate(LocalDate.now().minusDays(5));
		payment.setPaymentDate(LocalDate.now());
		payment.setBill(bill);
		
		double fineAmount = paymentService.latePaidAmount(payment);
		
		assertEquals(65.0, fineAmount);
	}
	
	@Test
	public void testDeletePaymentById() throws PaymentNotFoundException {
		int id = 1;
		Optional<Payment> payment = Optional.of(new Payment());
		
		when(paymentRepository.findById(id)).thenReturn(payment);
		
		assertDoesNotThrow(() -> paymentService.deletePaymentById(id));
		verify(paymentRepository, times(1)).deleteById(id);
	}
	
	@Test
	public void testDeletePaymentById_PaymentNotFound() {
		int id = 1;
		Optional<Payment> payment = Optional.empty();
		
		when(paymentRepository.findById(id)).thenReturn(payment);
		
		assertThrows(PaymentNotFoundException.class, () -> paymentService.deletePaymentById(id));
		verify(paymentRepository, never()).deleteById(anyInt());
	}
	
	@Test
	public void testGetAllPaymentRecords() {
		List<Payment> payments = new ArrayList<>();
		payments.add(new Payment());
		payments.add(new Payment());
		
		when(paymentRepository.findAll()).thenReturn(payments);
		
		List<Payment> result = paymentService.getAllPaymentRecords();
		
		assertEquals(2, result.size());
		verify(paymentRepository, times(1)).findAll();
	}
	
	@Test
	public void testGetPaymentById() throws PaymentNotFoundException {
		int id = 1;
		Payment payment = new Payment();
		Optional<Payment> optional = Optional.of(payment);
		
		when(paymentRepository.findById(id)).thenReturn(optional);
		
		Optional<Payment> result = paymentService.getPaymentById(id);
		
		assertTrue(result.isPresent());
		assertEquals(payment, result.get());
		verify(paymentRepository, times(1)).findById(id);
	}
	
	@Test
	public void testGetPaymentById_PaymentNotFound() {
		int id = 1;
		Optional<Payment> optional = Optional.empty();
		
		when(paymentRepository.findById(id)).thenReturn(optional);
		
		assertThrows(PaymentNotFoundException.class, () -> paymentService.getPaymentById(id));
		verify(paymentRepository, times(1)).findById(id);
	}
	

}

