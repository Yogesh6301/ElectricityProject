package com.electricity.api.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.electricity.api.data.UserRepository;
import com.electricity.api.dto.Message;
import com.electricity.api.model.Customer;
import com.electricity.api.model.Meter;
import com.electricity.api.model.User;
import com.electricity.api.service.CustomerService;
import com.electricity.api.service.MeterService;


//@CrossOrigin (origins = {"http://localhost:3000"})
@RestController
@CrossOrigin(origins = {"*"})
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MeterService meterService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/api/customer/add/{mid}")
	public ResponseEntity<Object> postCustomer(@PathVariable("mid") int meterId,
												
	                                           @RequestBody Customer customer) {
	    // Fetch User info from customer input and save it in DB 
	    User user = customer.getUser(); // Assuming you have userName and password
	    
	    // Set the user's role as "CUSTOMER"
	    user.setRole("CUSTOMER");
	    
	    // Convert plain text password into encoded text
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    // Attach encoded password to user
	    user.setPassword(encodedPassword);
	    
	    // Save the user to the userRepository
	    user = userRepository.save(user);
	    
	    // Attach user object to customer
	    customer.setUser(user);
	    
	    // Set the meter using the provided meter ID
	    Optional<Meter> optionalMeter = meterService.getMeterById(meterId);
	    if (!optionalMeter.isPresent()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Meter ID");
	    }
	    Meter meter = optionalMeter.get();
	    customer.setMeter(meter);
	    
	    // Save the customer using the customerService
	    customerService.insertCustomer(customer);
	    
	    // Return a success message in the response body
	    Message message = new Message();
	    message.setMsg("Customer Registration Is Done");
	    
	    return ResponseEntity.status(HttpStatus.OK).body(message);
	}




		@GetMapping("/api/customer/getall")
		public List<Customer> getAllCustomer(){
			List<Customer> list = customerService.getAllCustomer();
			return list;
		}
		
		@GetMapping("/api/customer/{customerId}")
		public ResponseEntity<Object> getCustomerById(@PathVariable("customerId") int customerId) {
			Optional<Customer> optional = customerService.getCustomerById(customerId);
			if (!optional.isPresent())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");
			
			Customer customer = optional.get();
			return ResponseEntity.status(HttpStatus.OK).body(customer);
		}
		
		@PutMapping("/api/customer/put/{customerId}")
		public ResponseEntity<String> updateCustomerById (@PathVariable("customerId") int customerId,@RequestBody Customer customer) {
			customerService.updateCustomerById(customer);
			return ResponseEntity.status(HttpStatus.OK).body("Customer is updated");
			}
		
		@DeleteMapping("/api/customer/delete/{customerId}")
		public ResponseEntity<String> deleteCustomerById(@PathVariable("customerId") int customerId,@RequestBody Customer customer) {

			customerService.deleteCustomerById(customer);
			return ResponseEntity.status(HttpStatus.OK).body("Customer is Deleted");
		}
		
		//Get customer by meter id
		
	    @GetMapping("/api/customer/meter/{mid}")
	    public List<Customer> getCustomerByMeterId(@PathVariable("mid") int mid) {
	    	
		        List<Customer> list = customerService.getCustomerByMeterId(mid);
		        return list;
	}
//	    @GetMapping("/api/customer/meter/{meterno}")
//	    public ResponseEntity<?> getCustomerDetailsByMeterNo(@PathVariable("meterno") int meterNo) {
//	        Optional<Meter> optionalMeter = meterService.findByMeterNo(meterNo);
//	        if (optionalMeter.isPresent()) {
//	            Meter meter = optionalMeter.get();
//	            Customer customer = meter.getCustomer();
//	            return ResponseEntity.ok(customer);
//	        } else {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( "Not Found");
//	        }
	    }


