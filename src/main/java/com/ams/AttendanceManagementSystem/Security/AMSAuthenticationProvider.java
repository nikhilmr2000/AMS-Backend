package com.ams.AttendanceManagementSystem.Security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ams.AttendanceManagementSystem.Entity.Register;

import com.ams.AttendanceManagementSystem.Repository.RegisterRepo;

@Configuration
public class AMSAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	public RegisterRepo repo;
	
	@Autowired
	public PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username=authentication.getName();
		String secretcode=authentication.getCredentials().toString();
		
		List<Register> allUsers=repo.findAll();
		
		for(Register user:allUsers) {
			if(user.getUsername().equals(username) && passwordEncoder.matches(secretcode,user.getSecretcode())) {
				List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority("user"));
				
				return new UsernamePasswordAuthenticationToken(user.getUsername(),secretcode,authorities);
		
			}
		}
		throw new BadCredentialsException("Invalid Username / Password");
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
