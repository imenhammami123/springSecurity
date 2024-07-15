package com.example.spring.security.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	public JWTAuthorizationFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	  @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	        response.addHeader("Access-Control-Allow-Origin","*");
	        response.addHeader("Access-Control-Allow-Headers","Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,authorization");
	        response.addHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
	        response.addHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT,PATCH");
	        if(request.getMethod().equals("OPTIONS")){
	            response.setStatus(HttpServletResponse.SC_OK);
	        }
	     
	        else{
	            String jwt = request.getHeader(SecurityConstant.HEADER_STRING);
	            if(jwt==null || !jwt.startsWith(SecurityConstant.TOKEN_Prefix)) {
	                filterChain.doFilter(request,response);return;
	            }
	            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityConstant.SECRET)).build();
	            DecodedJWT decodedJWT=verifier.verify(jwt.substring(SecurityConstant.TOKEN_Prefix.length()));
	            String username = decodedJWT.getSubject();
	            List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
	            Collection<GrantedAuthority> authorities = new ArrayList<>();
	            roles.forEach(rn->{
	                authorities.add(new SimpleGrantedAuthority(rn));
	            });
	            UsernamePasswordAuthenticationToken user=
	                    new UsernamePasswordAuthenticationToken(username,null,authorities);
	            SecurityContextHolder.getContext().setAuthentication(user);
	            filterChain.doFilter(request,response);
	        }
	    }
	}
