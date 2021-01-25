package com.example.demoThyme.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demoThyme.models.Staff;
import com.example.demoThyme.models.Student;

public interface StaffRepo extends JpaRepository<Staff, Integer>{

	
	  @Query(value="SELECT * FROM staff WHERE staff.email= :email", nativeQuery=true) 
	  public Staff getStaffByEmail(@Param("email") String email);
	 
	  @Query(value = "SELECT * FROM Staff WHERE Staff.staff_Id= :id", nativeQuery = true)
		public Staff getStaffById(@Param("id") int id);
}
