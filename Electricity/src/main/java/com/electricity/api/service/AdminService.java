package com.electricity.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.electricity.api.data.AdminRepository;

import com.electricity.api.model.Admin;


@Service

public class AdminService {

	@Autowired

	private AdminRepository adminRepository;

	public void insertAdmin(Admin admin) {

		// TODO Auto-generated method stub

		adminRepository.save(admin);

	}

	public List<Admin> getAllAdmins() {

		// TODO Auto-generated method stub

		return adminRepository.findAll();

	}

	public Optional<Admin> getAdminById(int id) {
		// TODO Auto-generated method stub
		Optional<Admin> optional = adminRepository.findById(id);
		return optional;
	}

	public void updateAdminById(Admin admin) {

		adminRepository.save(admin);

		// TODO Auto-generated method stub

	}

	public void deleteAdminById(Admin admin) {

		// TODO Auto-generated method stub

		adminRepository.delete(admin);

	}
}
