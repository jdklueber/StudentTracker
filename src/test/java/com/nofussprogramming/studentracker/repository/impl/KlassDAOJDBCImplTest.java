package com.nofussprogramming.studentracker.repository.impl;

import com.nofussprogramming.studentracker.controller.exceptions.NotFoundException;
import com.nofussprogramming.studentracker.model.Klass;
import com.nofussprogramming.studentracker.model.Roster;
import com.nofussprogramming.studentracker.model.Student;
import com.nofussprogramming.studentracker.repository.StudentDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KlassDAOJDBCImplTest {
    @Autowired
    JdbcTemplate db;

    @Autowired
    KlassDAOJDBCImpl dao;

    @Autowired
    StudentDAO studentDAO;

    Klass testKlass;
    final String expectedName = "The Class to End All Classes";
    final LocalDate expectedStartDate = LocalDate.now();
    final LocalDate expectedEndDate = LocalDate.now().plusDays(30);

    @BeforeEach
    void setUp() {
        db.execute("call set_known_good_state()");

        Student testStudent = new Student();
        testStudent.setFirstName("Jason");
        testStudent.setLastName("Klueber");
        studentDAO.save(testStudent);

        Roster testRoster = new Roster();
        testRoster.setStudent(testStudent);
        testRoster.setStudentId(testStudent.getId());
        testRoster.setActive(true);

        testKlass = new Klass();
        testKlass.setName(expectedName);
        testKlass.setStartDate(expectedStartDate);
        testKlass.setEndDate(expectedEndDate);
        testKlass.getRoster().add(testRoster);

        testKlass = dao.save(testKlass);
    }

    @Test
    void getById() {
        assertEquals(testKlass, dao.getById(testKlass.getId()));
    }

    @Test
    void saveAsUpdate() {
        Student test2 = new Student();
        test2.setLastName("foo");
        test2.setFirstName("bar");
        studentDAO.save(test2);

        Roster r = new Roster();
        r.setStudentId(test2.getId());
        r.setStudent(test2);
        testKlass.getRoster().add(r);

        testKlass.getRoster().get(0).setActive(false);

        dao.save(testKlass);

        assertEquals(testKlass, dao.getById(testKlass.getId()));

    }

    @Test
    void testDeleteARoster() {
        testKlass.getRoster().remove(0);
        dao.save(testKlass);
        assertEquals(testKlass, dao.getById(testKlass.getId()));
    }

    @Test
    void getAll() {
        assertEquals(1, dao.getAll().size());
    }

    @Test
    void delete() {
        Klass actual = dao.delete(testKlass.getId());
        assertEquals(testKlass, actual);
        assertThrows(NotFoundException.class, () -> dao.getById(testKlass.getId()));
        assertEquals(0, dao.getAll().size());

    }
}