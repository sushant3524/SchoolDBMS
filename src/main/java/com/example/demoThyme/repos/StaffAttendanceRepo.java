package com.example.demoThyme.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoThyme.models.Staff;

public interface StaffAttendanceRepo extends JpaRepository<Staff, Integer> {

}
