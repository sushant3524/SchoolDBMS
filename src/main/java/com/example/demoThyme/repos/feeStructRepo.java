package com.example.demoThyme.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demoThyme.models.FeesStructure;


public interface feeStructRepo extends JpaRepository<FeesStructure, String> {
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM fees_structure WHERE fees_structure.clss= :clss", nativeQuery=true)
	public void deleteByClss(@Param("clss") String clss);
	
	@Query(value = "SELECT * FROM fees_structure WHERE fees_structure.clss= :clss", nativeQuery = true)
	public FeesStructure getFeesStructureById(@Param("clss") String clss);
	
	@Query(value = "SELECT fees FROM fees_structure WHERE fees_structure.clss= :clss", nativeQuery = true)
	public int getFeesById(@Param("clss") String clss);
	
	@Query(value = "SELECT * FROM fees_structure", nativeQuery = true)
	public List<FeesStructure> allFeesStructure();
}
