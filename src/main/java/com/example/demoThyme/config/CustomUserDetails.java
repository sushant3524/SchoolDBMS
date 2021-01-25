package com.example.demoThyme.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demoThyme.models.Staff;

public class CustomUserDetails implements UserDetails {

	Staff staff;
	public CustomUserDetails(Staff staff) {
		
		this.staff = staff;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority x=new SimpleGrantedAuthority(staff.getRole());
		return List.of(x);
	}

	@Override
	public String getPassword() {
		
		return staff.getPassword();
	}

	@Override
	public String getUsername() {
		
		return staff.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
