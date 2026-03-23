package io.firstproject.springstarterproject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long>{

	List<Student> findByDepartment(String department);
	List<Student> findByYear(int year);
	List<Student> findByDepartmentAndYear(String department,int year);
	
}
