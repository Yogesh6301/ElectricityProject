package com.electricity.api.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.electricity.api.model.User;


public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.username=?1")
	User getUserByUsername(String username);

}
