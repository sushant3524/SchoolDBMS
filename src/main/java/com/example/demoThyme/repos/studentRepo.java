package com.example.demoThyme.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demoThyme.models.FeesStructure;
import com.example.demoThyme.models.Student;

public interface studentRepo extends JpaRepository<Student, Integer> {
	@Modifying
	@Transactional
	@Query(value="DELETE FROM Student WHERE Student.stu_Id= :id", nativeQuery=true)
	public void deleteById(@Param("id") int id);
	
	@Query(value = "SELECT * FROM Student WHERE Student.stu_Id= :id", nativeQuery = true)
	public Student getStudentById(@Param("id") int id);
	
	@Query(value = "SELECT * FROM Student", nativeQuery = true)
	public List<Student> allStudent();
	
}
