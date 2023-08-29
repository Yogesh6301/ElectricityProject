package com.electricity.api.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

   

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<String> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        // Customize the response entity and error message based on your requirements
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    @ExceptionHandler(BillNotFoundException.class)
    public ResponseEntity<String> handleBillNotFoundException(BillNotFoundException ex) {
        // Customize the response entity and error message based on your requirements
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    
}
