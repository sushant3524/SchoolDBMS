package com.example.demoThyme.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Salary {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int salary_id;
	
	private LocalDate date;
	private int amount;
	
	public int getSalary_id() {
		return salary_id;
	}
	public void setSalary_id(int salary_id) {
		this.salary_id = salary_id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Salary() {
		date=LocalDate.now();
		amount=0;
	}
	public Salary(int amt) {
		date=LocalDate.now();
		amount=amt;
	}
	
}
