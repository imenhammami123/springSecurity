package com.example.spring.security.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.security.Entities.AppRole;



public interface AppRoleRepository extends JpaRepository<AppRole, Long>{
	
	AppRole findByRoleName(String roleName);

}
