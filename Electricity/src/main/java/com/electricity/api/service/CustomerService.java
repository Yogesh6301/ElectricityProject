package com.electricity.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.electricity.api.data.CustomerRepository;
import com.electricity.api.data.MeterRepository;
import com.electricity.api.model.Customer;
import com.electricity.api.model.Meter;


@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	public void insertCustomer(Customer customer) {
		 customerRepository.save(customer);
		// TODO Auto-generated method stub
		
	 }
	
	public List<Customer> getAllCustomer() {
		// TODO Auto-generated method stub
		return customerRepository.findAll();
	}
	
	public Optional<Customer> getCustomerById(int id) {
		// TODO Auto-generated method stub
		Optional<Customer> optional = customerRepository.findById(id);
		return optional;
	}
	
	public void updateCustomerById(Customer customer) {
		// TODO Auto-generated method stub
		customerRepository.save(customer);
		
	}
	
	public void deleteCustomerById(Customer customer) {
		// TODO Auto-generated method stub
		customerRepository.delete(customer);
		
	}
	
	//Get customer by  Meter ID
		public List<Customer> getCustomerByMeterId(int mid) {
			// Fetch all customers from the DB 
			List<Customer> list = customerRepository.findAll();
			
			List<Customer> filteredList = 
					list.stream() 
						.filter(e->e.getMeter().getId() == mid)
						.collect(Collectors.toList());
			
			return filteredList;
		}

	
		@Service
		public class MeterService {
		    @Autowired
		    private MeterRepository meterRepository;

		    public Optional<Meter> findByMeterNo(int meterNo) {
		        return ((MeterService) meterRepository).findByMeterNo(meterNo);
		    }
		}

	}


