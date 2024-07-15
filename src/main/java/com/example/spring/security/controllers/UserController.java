package com.example.spring.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.security.Entities.AppUser;
import com.example.spring.security.Entities.RegistrationForm;
import com.example.spring.security.Repositories.AppUserRepository;
import com.example.spring.security.Services.AccountUser;

@RestController
public class UserController {
	@Autowired
	private AppUserRepository appRepository;
	@Autowired
	private AccountUser accountUser;

	@GetMapping("/bn")
	public void test() {
		System.out.print("bn");
	}
	
	
	@PostMapping("/signup")
	public AppUser signup(@RequestBody RegistrationForm request) {
		String username = request.getUsername();
		AppUser user = appRepository.findByUsername(username);
		if (user != null)
			throw new RuntimeException("this user already exist, try with another username");
		String password = request.getPassword();
		String repassword = request.getRepassword();
		if (!password.equals(repassword)) {
			throw new RuntimeException("you must confirm your password");
		}
		AppUser appUser = new AppUser();
		appUser.setUsername(username);
		appUser.setPassword(password);
		accountUser.addNewUser(appUser);
		accountUser.addRoleToUser(username, "USER");
		return appUser;
	}

}
