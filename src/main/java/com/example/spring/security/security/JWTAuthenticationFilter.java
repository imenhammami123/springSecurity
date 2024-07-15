package com.example.spring.security.security;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.spring.security.Entities.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	


	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		AppUser user = null;
		
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filter, Authentication authResult) throws IOException, ServletException {
	
		User springUser = (User) authResult.getPrincipal();
		
		   List<String> roles = new ArrayList();
	        authResult.getAuthorities().forEach(a->{
	            roles.add(a.getAuthority());
	        });
		String jwtToken = JWT.create()
				.withSubject(springUser.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+SecurityConstant.ExperationDate))
				.withArrayClaim("roles",roles.toArray(new String[roles.size()]))
				.sign(Algorithm.HMAC256(SecurityConstant.SECRET));
				
				
		response.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_Prefix+jwtToken);
	}
	
	
	
	
}
