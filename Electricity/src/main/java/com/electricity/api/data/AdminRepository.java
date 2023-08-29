package com.electricity.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electricity.api.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
