package com.example.demoThyme.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AttendanceStaff {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int attendance_id;
	
	private LocalDate date;
	
	public int getAttendance_id() {
		return attendance_id;
	}
	public void setAttendance_id(int attendance_id) {
		this.attendance_id = attendance_id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public AttendanceStaff() {
		date=LocalDate.now();
	}
	
}
