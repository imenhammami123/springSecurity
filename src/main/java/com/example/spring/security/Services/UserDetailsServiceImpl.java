package com.example.spring.security.Services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring.security.Entities.AppUser;
import com.example.spring.security.Repositories.AppUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private AppUserRepository appUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = appUserRepository.findByUsername(username);
		if(user == null) throw new UsernameNotFoundException(username);
		Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		user.getRoles().forEach(u->{
			roles.add(new SimpleGrantedAuthority(u.getRoleName()));
		});
		return new User(user.getUsername(), user.getPassword(), roles);
	}
	
	
}
