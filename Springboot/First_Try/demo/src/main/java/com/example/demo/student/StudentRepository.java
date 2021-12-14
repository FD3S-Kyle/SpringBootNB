package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    //SELECT * FROM student WHERE email = ? , this the sql equivalent
    //@Query("SELECT s FROM Student s WHERE s.email = ?1"); // this is more specific query
    //OR
    Optional<Student> findStudentByEmail(String email);
}
