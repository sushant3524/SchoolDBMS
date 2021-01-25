package com.example.demoThyme.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Resources {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int resourceId;
	
	private String name;
	
	private int count;
	
	private int threshold_count;

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getThreshold_count() {
		return threshold_count;
	}

	public void setThreshold_count(int threshold_count) {
		this.threshold_count = threshold_count;
	}

	public Resources() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
