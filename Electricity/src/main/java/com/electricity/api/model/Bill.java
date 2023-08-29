package com.electricity.api.model;

import java.time.LocalDate;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;

import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Bill {

    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    private int consumerNumber;

    private int consumedUnits;

    private String billMonth;

    private LocalDate billDate;

    private double amount;

    private LocalDate dueDate;
    
    @ManyToOne
    private Customer customer;
    
    @ManyToOne
    private Meter meter;
    
   

    

 

 

}
