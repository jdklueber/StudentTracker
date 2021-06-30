package com.nofussprogramming.studentracker.repository.impl;

import com.nofussprogramming.studentracker.controller.exceptions.NotFoundException;
import com.nofussprogramming.studentracker.model.Tag;
import com.nofussprogramming.studentracker.repository.TagDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagDAOJDBCImplTest {
    @Autowired
    TagDAO tagDao;

    @Autowired
    JdbcTemplate db;

    @BeforeEach
    void setUp() {
        db.execute("call set_known_good_state()");
    }

    @Test
    void getById() {
        Tag actual = tagDao.getById(1);
        Tag expected = new Tag();
        expected.setId(1);
        expected.setTag("Bio");
        assertEquals(expected, actual);
    }

    @Test
    void getAll() {
        List<Tag> tags = tagDao.getAll();
        assertEquals(8, tags.size());
    }

    @Test
    void saveAsAdd() {
        Tag newTag = new Tag();
        String expectedTagString = "Hello, World!";
        newTag.setTag(expectedTagString);

        Tag actual = tagDao.save(newTag);
        assertNotNull(actual.getId());
        assertEquals(expectedTagString, actual.getTag());

        assertEquals(actual, tagDao.getById(actual.getId())); //Verify that the save actually happened
    }

    @Test
    void saveAsUpdate() {
        String expectedUpdateString = "Hello, world!";
        Tag updatedTag = tagDao.getById(1);
        updatedTag.setTag(expectedUpdateString);

        Tag actual = tagDao.save(updatedTag);
        assertEquals(1, actual.getId()); //Make sure that the ID didn't report changed
        assertEquals(updatedTag, tagDao.getById(1)); //Verify that the tag in the DB matches the updated change


    }

    @Test
    void delete() {
        int initalSize = tagDao.getAll().size();
        Tag expected = tagDao.getById(1);
        Tag actual = tagDao.delete(1);
        assertEquals(expected, actual);
        assertEquals(initalSize-1, tagDao.getAll().size());
        assertThrows(NotFoundException.class, () -> tagDao.getById(1));

    }
}