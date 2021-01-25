package com.example.demoThyme.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demoThyme.models.SalaryStructure;

public interface SalaryStructRepo extends JpaRepository<SalaryStructure, String> {

	
	  @Modifying
	  
	  @Transactional
	 
	  @Query(value="DELETE FROM salary_structure WHERE salary_structure.designation= :designation", nativeQuery=true) 
	  public void deleteByDesignation(@Param("designation") String designation);
	  
	  @Query(value ="SELECT * FROM salary_structure WHERE salary_structure.designation= :designation", nativeQuery
	  = true) public SalaryStructure getFeesStructureById(@Param("designation") String designation);
	  
	  @Query(value ="SELECT salary FROM salary_structure WHERE salary_structure.designation= :designation", nativeQuery = true) 
	  public int getSalaryById(@Param("designation") String designation);
	  
	  @Query(value = "SELECT * FROM salary_structure", nativeQuery = true) public
	  List<SalaryStructure> allSalaryStructure();
	 
}
