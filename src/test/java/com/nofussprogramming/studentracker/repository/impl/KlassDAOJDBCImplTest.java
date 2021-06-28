package com.nofussprogramming.studentracker.repository.impl;

import com.nofussprogramming.studentracker.model.Klass;
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

    Klass testKlass;
    String expectedName = "The Class to End All Classes";
    LocalDate expectedStartDate = LocalDate.now();
    LocalDate expectedEndDate = LocalDate.now().plusDays(30);

    @BeforeEach
    void setUp() {
        db.execute("call set_known_good_state()");

        testKlass = new Klass();
        testKlass.setName(expectedName);
        testKlass.setStartDate(expectedStartDate);
        testKlass.setEndDate(expectedEndDate);

        testKlass = dao.save(testKlass);
    }

    @Test
    void getById() {
        assertEquals(testKlass, dao.getById(testKlass.getId()));
    }

    @Test
    void getAll() {
        assertEquals(1, dao.getAll().size());
    }

    @Test
    void saveAsUpdate() {
        testKlass.setStartDate(LocalDate.now().plusMonths(2));
        testKlass.setEndDate(LocalDate.now().plusMonths(5));
        testKlass.setName("Doo be doo");
        dao.save(testKlass);
        assertEquals(testKlass, dao.getById(testKlass.getId()));
        assertEquals(1, dao.getAll().size());
    }

    @Test
    void delete() {
        Klass actual = dao.delete(testKlass.getId());
        assertEquals(testKlass, actual);
        assertNull(dao.getById(testKlass.getId()));
        assertEquals(0, dao.getAll().size());
    }
}