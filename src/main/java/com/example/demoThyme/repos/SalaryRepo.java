package com.example.demoThyme.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoThyme.models.Salary;

public interface SalaryRepo extends JpaRepository<Salary, Integer>{

}
