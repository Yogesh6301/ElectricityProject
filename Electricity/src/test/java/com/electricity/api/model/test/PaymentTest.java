package com.electricity.api.model.test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.electricity.api.model.Bill;
import com.electricity.api.model.Customer;
import com.electricity.api.model.Payment;

class PaymentTest {

    @Mock
    private Customer customer;

    @Mock
    private Bill bill;

    @InjectMocks
    private Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        payment = new Payment();
        payment.setId(1);
        payment.setTotalAmount(100.0);
        payment.setPaymentDate(LocalDate.now());
        payment.setTransactionId("123456");
        payment.setCustomer(customer);
        payment.setBill(bill);
    }

    @Test
    void testGettersAndSetters() {
        // Verify getters
        assertEquals(1, payment.getId());
        assertEquals(100.0, payment.getTotalAmount());
        assertEquals(LocalDate.now(), payment.getPaymentDate());
        assertEquals("123456", payment.getTransactionId());
        assertEquals(customer, payment.getCustomer());
        assertEquals(bill, payment.getBill());

        // Verify setters
        payment.setId(2);
        assertEquals(2, payment.getId());

        payment.setTotalAmount(200.0);
        assertEquals(200.0, payment.getTotalAmount());

        LocalDate newDate = LocalDate.of(2022, 1, 1);
        payment.setPaymentDate(newDate);
        assertEquals(newDate, payment.getPaymentDate());

        payment.setTransactionId("654321");
        assertEquals("654321", payment.getTransactionId());

        Customer newCustomer = new Customer();
        payment.setCustomer(newCustomer);
        assertEquals(newCustomer, payment.getCustomer());

        Bill newBill = new Bill();
        payment.setBill(newBill);
        assertEquals(newBill, payment.getBill());
    }
}
