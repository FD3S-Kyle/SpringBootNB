package com.example.demo.student;

// import java.time.*;
import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	@Autowired
    public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getStudents()
	{
		return studentRepository.findAll(); //finds all the students
	}

    public void addNewStudent(Student student) 
	{
		Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail()); //finds email
		
		if(studentOptional.isPresent())  //checks if the email is present
		{
			throw new IllegalStateException("email taken"); //if an email is present then throw this exception
		}

		studentRepository.save(student); //save student email
    }

	public void deleteStudent(Long studentID) //delete method
	{
		boolean exists = studentRepository.existsById(studentID); //checks if student exist by their id
		
		if(!exists)
		{
			throw new IllegalStateException("student with id " + studentID + " does not exists"); //throws exception if the id does not exists
		}

		studentRepository.deleteById(studentID); //delete student via their id
	}

	@Transactional //this annotation this allows for values within the db to be updated without queries
    public void updateStudent(Long studentID, String name, String email) 
	{
		Student student = studentRepository.findById(studentID).orElseThrow(() -> new IllegalStateException("student with id " + studentID + " does not exist"));

		if(name != null && name.length() > 0 && !Objects.equals(student.getName(), name))
		{
			student.setName(name);
		}

		if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email))
		{
			Optional<Student> studentOptional = studentRepository.findStudentByEmail(email); //find students email

			if(studentOptional.isPresent()) //checks if email is taken, if so throw exception
			{
				throw new IllegalStateException("email taken");
			}

			student.setEmail(email); //update email via the setter function
		}


    }
}
