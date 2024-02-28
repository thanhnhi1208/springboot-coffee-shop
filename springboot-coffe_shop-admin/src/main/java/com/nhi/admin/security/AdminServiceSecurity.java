package com.nhi.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nhi.libary.model.Admin;
import com.nhi.libary.repository.AdminRepository;

@Service
public class AdminServiceSecurity implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = this.adminRepository.findByEmail(username);
		
		if(admin == null) {
			throw new UsernameNotFoundException("Could not find "+username);
		}
				
		return new AdminDetails(admin);
	}
}
