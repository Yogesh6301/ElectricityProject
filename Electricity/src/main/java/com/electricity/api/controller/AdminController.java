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
import com.electricity.api.model.Admin;
import com.electricity.api.model.User;
import com.electricity.api.service.AdminService;
import com.electricity.api.util.LoggerUtil;


@RestController
@CrossOrigin(origins = {"*"})
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	// ADMIN Post API

	//@PostMapping("api/admin/add")
//
//	public ResponseEntity<String> postAdmin(@RequestBody Admin admin) {
//
//		adminService.insertAdmin(admin);
//
//		return ResponseEntity.status(HttpStatus.OK).body("Admin added in the DB");
//
//	}
	
	//Post api with user
	@PostMapping("/api/admin/add")
	public ResponseEntity<String> postAdmin(@RequestBody Admin admin) {
		
		//Fetch User info from executive input and save it in DB 
		User user = admin.getUser(); //I have userName and password 
		//I will assign the role
		user.setRole("ADMIN");

		//Converting plain text password into encoded text
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		//attach encoded password to user
		user.setPassword(encodedPassword);

		user  = userRepository.save(user);


		//Attach user object to executive
		admin.setUser(user);
		adminService.insertAdmin(admin);
		LoggerUtil.logInfo("Admin details are Posted ");
		return ResponseEntity.status(HttpStatus.OK).body("Admin Added in DB");

	}
	


	// Get Api

	@GetMapping("api/admin/getall")

	public List<Admin> getAllAdmins() {

		List<Admin> list = adminService.getAllAdmins();

		return list;

	}

	@GetMapping("/api/admin/{adminId}")
	public ResponseEntity<Object> getAdminById(@PathVariable("adminId") int adminId) {
		Optional<Admin> optional = adminService.getAdminById(adminId);
		if (!optional.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");

		Admin admin = optional.get();
		return ResponseEntity.status(HttpStatus.OK).body(admin);
	}

	// Put ApI

	@PutMapping("/api/admin/{id}")

	public ResponseEntity<String> updateAdminById(@PathVariable("id") int id, @RequestBody Admin admin) {

		adminService.updateAdminById(admin);

		return ResponseEntity.status(HttpStatus.OK).body("Admin is Updated");

	}

	// Delete Api

	@DeleteMapping("/api/admin/{id}")

	public ResponseEntity<String> deleteAdminById(@PathVariable("id") int id, @RequestBody Admin admin) {

		adminService.deleteAdminById(admin);

		return ResponseEntity.status(HttpStatus.OK).body("Admin is Deleted");

	}

}


