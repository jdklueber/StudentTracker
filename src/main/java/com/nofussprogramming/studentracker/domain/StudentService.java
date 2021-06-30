package com.nofussprogramming.studentracker.domain;

import com.nofussprogramming.studentracker.model.Student;
import com.nofussprogramming.studentracker.repository.LogDAO;
import com.nofussprogramming.studentracker.repository.RosterDAO;
import com.nofussprogramming.studentracker.repository.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    StudentDAO students;
    LogDAO logs;
    RosterDAO rosters;

    @Autowired
    public StudentService(StudentDAO students, LogDAO logs, RosterDAO rosters) {
        this.students = students;
        this.logs = logs;
        this.rosters = rosters;
    }

    public List<Student> getAll() {
        return students.getAll();
    }

    public Student getById(Integer id) {
        return students.getById(id);
    }

    public Student saveStudent(Student student) {
        return students.save(student);
    }

    public Student deleteStudent(int id) {
        return students.delete(id);
    }
}
