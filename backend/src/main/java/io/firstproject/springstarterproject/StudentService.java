
package io.firstproject.springstarterproject;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import dto.StudentRequestDTO;
import dto.StudentResponseDTO;
@Service
public class StudentService {

	@Autowired
	private StudentRepository repository;
	
	public StudentResponseDTO addStudent(StudentRequestDTO dto)
	{
		Student student=new Student();
		student.setName(dto.getName());
		student.setEmail(dto.getEmail());
		student.setDepartment(dto.getDepartment());
		student.setYear(dto.getYear());
		student.setCgpa(dto.getCgpa());
		
		Student savedStudent=repository.save(student);
		return mapToResponseDTO(savedStudent);
	}
	public Page<StudentResponseDTO> getStudentsWithPagination(int page, int size, String sortBy)
	{
		Pageable pageable=PageRequest.of(page, size, Sort.by(sortBy));
		Page<Student> studentPage=repository.findAll(pageable);
		return studentPage.map(this::mapToResponseDTO);
	}
	public List<StudentResponseDTO> filterStudents(String department,Integer year)
	{
		List<Student> students;
		if(department!=null && year!=null)
		{
			students=repository.findByDepartmentAndYear(department, year);
		}
		else if(department!=null)
		{
			students=repository.findByDepartment(department);
		}
		else if(year!=null)
		{
			students=repository.findByYear(year);
		}
		else
		{
			students=repository.findAll();
		}
		return students.stream().map(this::mapToResponseDTO).toList();
	}
	
	
	public List<StudentResponseDTO> getAllStudents()
	{
		return repository.findAll().stream().map(this::mapToResponseDTO).toList();
	}
	
	public StudentResponseDTO getStudentById(Long id)
	{
		Student student= repository.findById(id).orElseThrow(()-> new StudentNotFoundException("Student not found with id "+id));
		return mapToResponseDTO(student);
	}
	
	public void deleteStudent(Long id)
	{
		repository.deleteById(id);
	}
	
	private StudentResponseDTO mapToResponseDTO(Student student)
	{
		StudentResponseDTO dto=new StudentResponseDTO();
		dto.setId(student.getId());
		dto.setName(student.getName());
		dto.setEmail(student.getEmail());
		dto.setDepartment(student.getDepartment());
		dto.setYear(student.getYear());
		dto.setCgpa(student.getCgpa());
		return dto;
		
	}
	public StudentResponseDTO updateStudent(Long id, StudentRequestDTO dto) {

	    Student student = repository.findById(id)
	            .orElseThrow(() -> new StudentNotFoundException("Student not found"));

	    student.setName(dto.getName());
	    student.setEmail(dto.getEmail());
	    student.setDepartment(dto.getDepartment());
	    student.setYear(dto.getYear());
	    student.setCgpa(dto.getCgpa());

	    Student updated = repository.save(student);

	    return mapToResponseDTO(updated);
	}
}
