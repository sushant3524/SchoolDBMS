package com.example.demoThyme.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SalaryStructure {

	@Id
	private String designation;
	private int salary;
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public SalaryStructure(String designation, int salary) {
		super();
		this.designation = designation;
		this.salary = salary;
	}
	public SalaryStructure() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
