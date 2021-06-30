package com.nofussprogramming.studentracker.repository;

import com.nofussprogramming.studentracker.controller.exceptions.DatabaseErrorException;
import com.nofussprogramming.studentracker.controller.exceptions.NotFoundException;
import com.nofussprogramming.studentracker.model.Student;

import java.util.List;

public interface StudentDAO {
    Student getById(int id) throws DatabaseErrorException, NotFoundException;
    List<Student> getAll() throws DatabaseErrorException, NotFoundException;
    Student save(Student student) throws DatabaseErrorException;
    Student delete(int id) throws DatabaseErrorException, NotFoundException;
}
