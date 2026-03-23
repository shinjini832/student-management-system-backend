package io.firstproject.springstarterproject;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dto.StudentRequestDTO;
import dto.StudentResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService service;
	
	@PostMapping
	public StudentResponseDTO addStudent(@Valid @RequestBody StudentRequestDTO studentDTO)
	{
		return service.addStudent(studentDTO);
	}
	@GetMapping("/paged")
	public Page<StudentResponseDTO> getStudentsPaged(
	        @RequestParam int page,
	        @RequestParam int size,
	        @RequestParam(defaultValue = "id") String sortBy) {

	    return service.getStudentsWithPagination(page, size, sortBy);
	}
	
	
	@GetMapping
	public List<StudentResponseDTO> getAllStudents()
	{
		return service.getAllStudents();
	}
	@GetMapping("/filter")
	public List<StudentResponseDTO> filterStudents(
	
		@RequestParam(required=false) String department,
		@RequestParam(required=false) Integer year)
	{
		
		return service.filterStudents(department, year);
	}
	
	@GetMapping("/{id}")
	public StudentResponseDTO getStudentById(@PathVariable Long id)
	{
		return service.getStudentById(id);
	}
	
	@DeleteMapping("/{id}")
	public void deleteStudent(@PathVariable Long id)
	{
		service.deleteStudent(id);
	}
}
