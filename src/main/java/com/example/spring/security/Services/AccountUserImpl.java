package com.example.spring.security.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring.security.Entities.AppRole;
import com.example.spring.security.Entities.AppUser;
import com.example.spring.security.Repositories.AppRoleRepository;
import com.example.spring.security.Repositories.AppUserRepository;


@Service
@Transactional
public class AccountUserImpl implements AccountUser{

	private AppUserRepository appUserRepository;
	private AppRoleRepository appRoleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	public AccountUserImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
		super();
		this.appUserRepository = appUserRepository;
		this.appRoleRepository = appRoleRepository;
	}

	@Override
	public AppUser addNewUser(AppUser appUser) {
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		return appUserRepository.save(appUser);
	}

	@Override
	public AppRole addNewRole(AppRole appRole) {
		
		return appRoleRepository.save(appRole);
	}

	@Override
	public void addRoleToUser(String username, String rolename) {
		AppRole appRole = appRoleRepository.findByRoleName(rolename);
		AppUser appUser = appUserRepository.findByUsername(username);
		appUser.getRoles().add(appRole);
		
	}

	@Override
	public AppUser loadUserByusername(String username) {
		
		return appUserRepository.findByUsername(username);
	}

	@Override
	public List<AppUser> listUsers() {
		
		return appUserRepository.findAll();
	}

}
