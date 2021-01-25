package com.example.demoThyme.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoThyme.models.Fees;

public interface FeeRepo extends JpaRepository<Fees, Integer>{

}
