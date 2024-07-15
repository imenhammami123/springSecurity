package com.example.spring.security.Services;

import java.util.List;

import com.example.spring.security.Entities.AppRole;
import com.example.spring.security.Entities.AppUser;




public interface AccountUser {
	
	AppUser addNewUser(AppUser appUser);
	AppRole addNewRole(AppRole appRole);
	void addRoleToUser(String username, String rolename);
	AppUser loadUserByusername(String username);
	List<AppUser> listUsers();
}
