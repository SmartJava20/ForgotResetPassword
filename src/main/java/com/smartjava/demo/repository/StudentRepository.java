package com.smartjava.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartjava.demo.model.StudentModel;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Integer>{

	StudentModel findByEmail(String email);

}
