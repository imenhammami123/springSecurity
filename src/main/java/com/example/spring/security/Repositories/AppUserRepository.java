package com.example.spring.security.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.security.Entities.AppUser;



public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	
	AppUser findByUsername(String username);

}
