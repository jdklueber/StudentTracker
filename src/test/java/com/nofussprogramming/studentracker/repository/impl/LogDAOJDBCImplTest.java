package com.nofussprogramming.studentracker.repository.impl;

import com.nofussprogramming.studentracker.controller.exceptions.NotFoundException;
import com.nofussprogramming.studentracker.model.Klass;
import com.nofussprogramming.studentracker.model.Log;
import com.nofussprogramming.studentracker.model.Student;
import com.nofussprogramming.studentracker.repository.KlassDAO;
import com.nofussprogramming.studentracker.repository.StudentDAO;
import com.nofussprogramming.studentracker.repository.TagDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogDAOJDBCImplTest {
    @Autowired
    LogDAOJDBCImpl dao;

    @Autowired
    JdbcTemplate db;

    @Autowired
    KlassDAO klasses;

    @Autowired
    StudentDAO students;

    @Autowired
    TagDAO tags;

    Klass testKlassA;
    Klass testKlassB;
    Student alpha;
    Student bravo;
    Student charlie;

    Log expected;

    @BeforeEach
    void setUp() {
        db.execute("call set_known_good_state()");

        testKlassA = new Klass();
        testKlassA.setName("Java 101");
        testKlassA.setStartDate(LocalDate.now());
        testKlassA.setEndDate(LocalDate.now().plusMonths(3));
        klasses.save(testKlassA);

        testKlassB = new Klass();
        testKlassB.setName("Java 102");
        testKlassB.setStartDate(LocalDate.now());
        testKlassB.setEndDate(LocalDate.now().plusMonths(3));
        klasses.save(testKlassB);

        alpha = new Student();
        alpha.setFirstName("Alpha");
        alpha.setLastName("Student A");
        students.save(alpha);
        bravo = new Student();
        bravo.setFirstName("Bravo");
        bravo.setLastName("Student B");
        students.save(bravo);
        charlie = new Student();
        charlie.setFirstName("Charlie");
        charlie.setLastName("Student C");
        students.save(charlie);

        expected = new Log();
        expected.setKlassId(testKlassA.getId());
        expected.setStudentId(alpha.getId());
        expected.setTimestamp(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)); //MySQL stores to the second, and I
                                                                                  // don't feel like dealing with this for real
                                                                                  //because it only matters if you're trying to
                                                                                  //verify that what you put in is what you got out
        expected.setTag(tags.getById(1));
        expected.setBody("This is the song that wouldn't end/it goes on and on my friend");

        dao.save(expected);
    }

    @Test
    void getById() {
        assertEquals(expected, dao.getById(expected.getId()));
    }

    @Test
    void getAll() {
        assertEquals(1, dao.getAll().size());
    }

    @Test
    void getAllForStudent() {
        assertEquals(1, dao.getAllForStudent(alpha.getId()).size());
        assertTrue(dao.getAllForStudent(alpha.getId()).contains(expected));
        assertEquals(0, dao.getAllForStudent(bravo.getId()).size());
    }

    @Test
    void getAllForKlass() {
        assertEquals(1, dao.getAllForKlass(testKlassA.getId()).size());
        assertTrue(dao.getAllForKlass(testKlassA.getId()).contains(expected));
        assertEquals(0, dao.getAllForKlass(testKlassB.getId()).size());
    }

    @Test
    void saveAsUpdate() {
        expected.setBody("doo be doo be do");
        Log actual = dao.save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void delete() {
        Log actual = dao.delete(expected.getId());
        assertEquals(expected, actual);
        assertEquals(0, dao.getAll().size());
    }
}