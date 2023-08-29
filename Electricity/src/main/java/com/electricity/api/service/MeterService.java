package com.electricity.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.electricity.api.data.MeterRepository;
import com.electricity.api.model.Meter;




@Service
public class MeterService {
	@Autowired
	private MeterRepository meterRepository;

	public void insertMeter(Meter meter) {
		 meterRepository.save(meter);
		
	}
	public Optional<Meter> getMeterById(int id) {
		Optional<Meter> optional = meterRepository.findById(id);
		// TODO Auto-generated method stub
		return optional;
	}
	

	 public List<Meter> getAllMeterNumbers() {
		// TODO Auto-generated method stub
		 return meterRepository.findAll();
	 }
	public Optional<Meter> findByMeterNo(int meterNo) {
		// TODO Auto-generated method stub
		return meterRepository.findById(meterNo);
	}
	


}
