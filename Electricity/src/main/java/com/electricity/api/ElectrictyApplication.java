package com.electricity.api;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.electricity.api.util.LoggerUtil;


@SpringBootApplication
public class ElectrictyApplication {
	public static void main(String[] args) {
		SpringApplication.run(ElectrictyApplication.class, args);
		LoggerUtil.logInfo("ELectricity Application Started");
		
	}

}
