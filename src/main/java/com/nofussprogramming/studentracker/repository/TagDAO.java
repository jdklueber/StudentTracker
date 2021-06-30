package com.nofussprogramming.studentracker.repository;

import com.nofussprogramming.studentracker.controller.exceptions.DatabaseErrorException;
import com.nofussprogramming.studentracker.controller.exceptions.NotFoundException;
import com.nofussprogramming.studentracker.model.Tag;

import java.util.List;

public interface TagDAO {
    Tag getById(int id) throws DatabaseErrorException, NotFoundException;
    List<Tag> getAll() throws DatabaseErrorException, NotFoundException;
    Tag save(Tag tag) throws DatabaseErrorException;
    Tag delete(int id) throws DatabaseErrorException, NotFoundException;
}
