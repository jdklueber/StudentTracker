package com.nofussprogramming.studentracker.controller;

import com.nofussprogramming.studentracker.domain.LogService;
import com.nofussprogramming.studentracker.domain.StudentService;
import com.nofussprogramming.studentracker.model.Log;
import com.nofussprogramming.studentracker.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    final StudentService studentService;
    final LogService logService;

    @Autowired
    public StudentController(StudentService studentService, LogService logService) {
        this.studentService = studentService;
        this.logService = logService;

    }

    @GetMapping
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable int id) {
        return studentService.getById(id);
    }

    @PostMapping
    public Integer addStudent(@RequestBody Student student) {
        Student s = studentService.saveStudent(student);
        return s.getId();
    }

    @PutMapping()
    public void updateStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
    }

    @DeleteMapping("/{id}")
    public Student deleteStudent(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/{id}/logs")
    public List<Log> getLogsForStudent(@PathVariable int id) {
        return logService.getLogsForStudent(id);
    }
}
