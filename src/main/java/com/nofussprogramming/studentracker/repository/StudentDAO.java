package com.nofussprogramming.studentracker.repository;

import com.nofussprogramming.studentracker.model.Student;

import java.util.List;

public interface StudentDAO {
    Student getById(int id);
    List<Student> getAll();
    Student save(Student student);
    Student delete(int id);
}
