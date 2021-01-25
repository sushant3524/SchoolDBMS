package com.example.demoThyme.models;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Fees {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int fees_id;
	
	private LocalDate date;
	private int amount;

	public int getFees_id() {
		return fees_id;
	}

	public void setFees_id(int fees_id) {
		this.fees_id = fees_id;
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

	public Fees()
	{
		this.date=LocalDate.now();
		this.amount=0;
	}
	public Fees(int amt)
	{
		this.date=LocalDate.now();
		this.amount=amt;
	}
}
