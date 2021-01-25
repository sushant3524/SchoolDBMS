package com.example.demoThyme.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoThyme.models.AttendanceStudent;

public interface AttendanceRepo extends JpaRepository<AttendanceStudent, Integer> {

}
