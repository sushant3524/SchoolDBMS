package com.example.demoThyme.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demoThyme.models.Staff;
import com.example.demoThyme.repos.StaffRepo;

public class StaffDetailServiceImpl implements UserDetailsService{

	@Autowired
	private StaffRepo staffRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Staff staff = staffRepo.getStaffByEmail(username);
		if(staff==null)
			throw new UsernameNotFoundException("No Staff Memeber with Given Email");
		CustomUserDetails cud= new CustomUserDetails(staff);
		
		return cud;
	}

}
