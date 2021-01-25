package com.example.demoThyme.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demoThyme.models.Resources;

public interface ResourceRepo extends JpaRepository<Resources,Integer> {

	@Modifying
	@Transactional
	@Query(value="DELETE FROM resources WHERE resources.resource_id= :id", nativeQuery=true)
	public void deleteById(@Param("id") int id);
	
	@Query(value = "SELECT * FROM resources WHERE resources.resource_id= :id", nativeQuery = true)
	public Resources getResourcesById(@Param("id") int id);
	
	@Query(value = "SELECT * FROM resources", nativeQuery = true)
	public List<Resources> allResources();
}
