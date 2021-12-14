package com.example.demo.student;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }
    
    @GetMapping //GET aspect
	public List<Student> getStudents()
	{
		return studentService.getStudents();
	}

    @PostMapping //POST aspect
    public void registerNewStudent(@RequestBody Student student) //takes the request body and maps it to a student
    {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentID}") //DELETE aspect via a route 
    public void deleteStudent(@PathVariable("studentID") Long studentID)
    {
        studentService.deleteStudent(studentID);
    }

    @PutMapping(path = "{studentID}")
    public void updateStudent(
                    @PathVariable("studentID") Long studentID,
                    @RequestParam(required = false) String name,
                    @RequestParam(required = false) String email)
    {
        studentService.updateStudent(studentID, name, email);
    }

}
