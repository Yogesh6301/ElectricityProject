package com.electricity.api.data;

import org.springframework.data.jpa.repository.JpaRepository;
import com.electricity.api.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer>{

}
