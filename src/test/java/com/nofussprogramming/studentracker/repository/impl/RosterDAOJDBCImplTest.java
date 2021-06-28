package com.nofussprogramming.studentracker.repository.impl;

import com.nofussprogramming.studentracker.model.Klass;
import com.nofussprogramming.studentracker.model.Roster;
import com.nofussprogramming.studentracker.model.Student;
import com.nofussprogramming.studentracker.repository.KlassDAO;
import com.nofussprogramming.studentracker.repository.StudentDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RosterDAOJDBCImplTest {
    @Autowired
    JdbcTemplate db;

    @Autowired
    StudentDAO students;

    @Autowired
    KlassDAO klasses;

    @Autowired
    RosterDAOJDBCImpl rosterDao;

    Klass testKlassA;
    Klass testKlassB;
    Student alpha;
    Student bravo;
    Student charlie;

    Roster testA;
    Roster testB;
    Roster testC;

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

        List<Roster> rosterList = new ArrayList<>();

        testA = new Roster();
        testA.setKlassId(testKlassA.getId());
        testA.setStudentId(alpha.getId());
        testA.setActive(true);
        rosterList.add(testA);

        testB = new Roster();
        testB.setKlassId(testKlassA.getId());
        testB.setStudentId(bravo.getId());
        testB.setActive(false);
        rosterList.add(testB);

        testC = new Roster();
        testC.setKlassId(testKlassB.getId());
        testC.setStudentId(charlie.getId());
        testC.setActive(true);
        rosterList.add(testC);

        rosterDao.saveAll(rosterList);

    }

    @Test
    void verifyTestData() {
        assertNotNull(testKlassA.getId());
        assertNotNull(testKlassB.getId());
        assertNotNull(alpha.getId());
        assertNotNull(bravo.getId());
        assertNotNull(charlie.getId());
        assertNotNull(testA.getId());
        assertNotNull(testB.getId());
        assertNotNull(testC.getId());
    }

    @Test
    void getById() {
        assertEquals(testA, rosterDao.getById(testA.getId()));
    }

    @Test
    void getAll() {
        List<Roster> actual = rosterDao.getAll();
        assertEquals(3, actual.size());
        assertTrue(actual.contains(testA));
        assertTrue(actual.contains(testB));
        assertTrue(actual.contains(testC));
    }

    @Test
    void getAllForKlass() {
        List<Roster> actual = rosterDao.getAllForKlass(testKlassB.getId());
        assertEquals(1, actual.size());
        assertFalse(actual.contains(testA));
        assertFalse(actual.contains(testB));
        assertTrue(actual.contains(testC));
    }

    @Test
    void getAllForStudent() {
        List<Roster> actual = rosterDao.getAllForStudent(bravo.getId());
        assertEquals(1, actual.size());
        assertFalse(actual.contains(testA));
        assertTrue(actual.contains(testB));
        assertFalse(actual.contains(testC));
    }

    @Test
    void saveAsUpdate() {
        testA.setActive(false);
        rosterDao.save(testA);
        assertEquals(testA, rosterDao.getById(testA.getId()));
        assertEquals(3, rosterDao.getAll().size());
    }

    @Test
    void delete() {
        Roster check = rosterDao.delete(testA.getId());
        assertEquals(testA, check);
        assertNull(rosterDao.getById(check.getId()));
    }
}