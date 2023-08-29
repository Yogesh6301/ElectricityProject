package com.electricity.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.electricity.api.data.BillRepository;
import com.electricity.api.exception.BillNotFoundException;
import com.electricity.api.exception.PaymentNotFoundException;
import com.electricity.api.model.Bill;



@Service
public class BillService extends Exception{
	
	@Autowired
	private BillRepository billRepository;
	
	private double calculateBill(long units) {
		double unitPrice = 2.5;
		return units*unitPrice;
	}
	public void insertBill(Bill bill) {
		
		bill.setAmount(calculateBill(bill.getConsumedUnits()));
		 billRepository.save(bill);
		
	 }
	
	public List<Bill> getAllBill() {
		// TODO Auto-generated method stub
		return billRepository.findAll();
	}
	
	public Optional<Bill> getBillById(int id) throws BillNotFoundException{
		
		Optional<Bill> bill = billRepository.findById(id);
		if (!bill.isPresent()) {
			throw new BillNotFoundException("Bill not found with ID: " + id);
		}
		return bill;
	}
	
	//Update
		public void updateBillById(Bill bill)throws BillNotFoundException {
			 Optional<Bill> optionalBill = billRepository.findById(bill.getId());
			    if (!optionalBill.isPresent()) {
			        throw new BillNotFoundException("Bill not found with ID: " + bill.getId());
			    }

			billRepository.save(bill);

		}

	//delete
		public void deleteBillById(Bill bill) throws BillNotFoundException {
			 Optional<Bill> optionalBill = billRepository.findById(bill.getId());
			    if (!optionalBill.isPresent()) {
			        throw new BillNotFoundException("Bill not found with ID: " + bill.getId());
			    }


			billRepository.delete(bill);

		}
	
	//Get bill by  customer ID
		public List<Bill> getBillByCustomerId(int cid) {
			// Fetch all bills from the DB 
			List<Bill> list = billRepository.findAll();
			
			List<Bill> filteredList = 
					list.stream() 
						.filter(e->e.getCustomer().getId() == cid)
						.collect(Collectors.toList());
			
			return filteredList;
		}
		
		//Get bill by meter ID
				public List<Bill> getBillByMeterId(int mid) {
					// Fetch all bills from the DB 
					List<Bill> list = billRepository.findAll();
					
					List<Bill> filteredList = 
							list.stream() 
								.filter(e->e.getMeter().getId() == mid)
								.collect(Collectors.toList());
					
					return filteredList;
				}
	

}
