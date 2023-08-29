package com.electricity.api.controller.test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.electricity.api.controller.PaymentController;
import com.electricity.api.exception.PaymentNotFoundException;
import com.electricity.api.model.Bill;
import com.electricity.api.model.Customer;
import com.electricity.api.model.Payment;
import com.electricity.api.service.BillService;
import com.electricity.api.service.CustomerService;
import com.electricity.api.service.PaymentService;
import com.electricity.api.util.LoggerUtil;

public class PaymentControllerTest {

	@Mock
	private PaymentService paymentService;

	@Mock
	private CustomerService customerService;

	@Mock
	private BillService billService;

	@InjectMocks
	private PaymentController paymentController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testBillPayment_ValidBillAndCustomer_ReturnsSuccessResponse() throws Exception {
		Payment payment = new Payment();
		Bill bill = new Bill();
		Customer customer = new Customer();
		Optional<Bill> optionalBill = Optional.of(bill);
		Optional<Customer> optionalCustomer = Optional.of(customer);

		when(billService.getBillById(1)).thenReturn(optionalBill);
		when(customerService.getCustomerById(1)).thenReturn(optionalCustomer);
		doNothing().when(paymentService).assign(payment);

		ResponseEntity<String> response = paymentController.BillPayment(payment, 1, 1);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Payment Succesfull!!!", response.getBody());
		verify(paymentService, times(1)).assign(payment);
	}

	@Test
	public void testBillPayment_InvalidBill_ReturnsBadRequestResponse() throws Exception {
		Payment payment = new Payment();
		Optional<Bill> optionalBill = Optional.empty();
		Optional<Customer> optionalCustomer = Optional.of(new Customer());

		when(billService.getBillById(1)).thenReturn(optionalBill);
		when(customerService.getCustomerById(1)).thenReturn(optionalCustomer);

		ResponseEntity<String> response = paymentController.BillPayment(payment, 1, 1);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("entered invalid billId", response.getBody());
		verify(paymentService, never()).assign(payment);
	}

	@Test
	public void testBillPayment_InvalidCustomer_ReturnsBadRequestResponse() throws Exception {
		Payment payment = new Payment();
		Optional<Bill> optionalBill = Optional.of(new Bill());
		Optional<Customer> optionalCustomer = Optional.empty();

		when(billService.getBillById(1)).thenReturn(optionalBill);
		when(customerService.getCustomerById(1)).thenReturn(optionalCustomer);

		ResponseEntity<String> response = paymentController.BillPayment(payment, 1, 1);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("entered invalid custId", response.getBody());
		verify(paymentService, never()).assign(payment);
	}

	@Test
	public void testGetAllPaymentRecords() {
		List<Payment> payments = new ArrayList<>();
		payments.add(new Payment());
		payments.add(new Payment());

		when(paymentService.getAllPaymentRecords()).thenReturn(payments);

		List<Payment> result = paymentController.getAllPaymentRecords();

		assertEquals(2, result.size());
		verify(paymentService, times(1)).getAllPaymentRecords();
	}

	@Test
	public void testGetPaymentById_ValidId_ReturnsPayment() throws PaymentNotFoundException {
		Payment payment = new Payment();
		Optional<Payment> optional = Optional.of(payment);

		when(paymentService.getPaymentById(1)).thenReturn(optional);

		ResponseEntity<Object> response = paymentController.getPaymentById(1);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(payment, response.getBody());
		verify(paymentService, times(1)).getPaymentById(1);
	}

	@Test
	public void testGetPaymentById_InvalidId_ReturnsBadRequestResponse() throws PaymentNotFoundException {
		Optional<Payment> optional = Optional.empty();

		when(paymentService.getPaymentById(1)).thenReturn(optional);

		ResponseEntity<Object> response = paymentController.getPaymentById(1);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Invalid ID given", response.getBody());
		verify(paymentService, times(1)).getPaymentById(1);
	}

	@Test
	public void testDeletePaymentById_ValidId_ReturnsSuccessResponse() throws PaymentNotFoundException {
		Payment payment = new Payment();

		doNothing().when(paymentService).deletePaymentById(1);

		ResponseEntity<String> response = paymentController.deletePaymentById(1, payment);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Payment Record is Deleted", response.getBody());
		verify(paymentService, times(1)).deletePaymentById(1);
	}

	@Test
	public void testGetPaymentByCustomerId_ValidId_ReturnsListOfPayments() throws Exception {
		List<Payment> payments = new ArrayList<>();
		payments.add(new Payment());
		payments.add(new Payment());

		when(paymentService.getPaymentByCustomerId(1)).thenReturn(payments);

		List<Payment> result = paymentController.getPaymentByCustomerId(1);

		assertEquals(2, result.size());
		verify(paymentService, times(1)).getPaymentByCustomerId(1);
	}
}
