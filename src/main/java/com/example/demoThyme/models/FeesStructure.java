package com.example.demoThyme.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FeesStructure {

	@Id
	private String clss;
	private int fees;
	public String getClss() {
		return clss;
	}
	public void setClss(String clss) {
		this.clss = clss;
	}
	public int getFees() {
		return fees;
	}
	public void setFees(int fees) {
		this.fees = fees;
	}
	public FeesStructure(String clss, int fees) {
		super();
		this.clss = clss;
		this.fees = fees;
	}
	public FeesStructure() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
