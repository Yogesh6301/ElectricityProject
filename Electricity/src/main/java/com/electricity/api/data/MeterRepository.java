package com.electricity.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electricity.api.model.Meter;


public interface MeterRepository extends JpaRepository<Meter, Integer>{

}
