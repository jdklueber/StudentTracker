package com.nofussprogramming.studentracker.repository.impl;

import com.nofussprogramming.studentracker.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class StudentDAOJDBCImplTest {
    @Autowired
    StudentDAOJDBCImpl dao;

    @Autowired
    JdbcTemplate db;

    Student testStudent;
    static final String expectedFirstName = "Jason";
    static final String expectedLastName = "Klueber";


    @BeforeEach
    void setUp() {
        db.execute("call set_known_good_state()");

        testStudent = new Student();
        testStudent.setFirstName(expectedFirstName);
        testStudent.setLastName(expectedLastName);
        testStudent = dao.save(testStudent);
    }

    @Test
    void getById() {
        assertEquals(testStudent, dao.getById(testStudent.getId()));
    }

    @Test
    void getAll() {
        assertEquals(1, dao.getAll().size());
    }

    @Test
    void saveAsUpdate() {
        testStudent.setFirstName("Angela");
        dao.save(testStudent);

        assertEquals(testStudent, dao.getById(testStudent.getId()));
        assertEquals(1, dao.getAll().size());
    }

    @Test
    void delete() {
        Student s = dao.delete(testStudent.getId());
        assertEquals(testStudent, s);
        assertEquals(0, dao.getAll().size());
    }
}