package com.nofussprogramming.studentracker.repository;

import com.nofussprogramming.studentracker.controller.exceptions.DatabaseErrorException;
import com.nofussprogramming.studentracker.controller.exceptions.NotFoundException;
import com.nofussprogramming.studentracker.model.Klass;

import java.util.List;

public interface KlassDAO {
    Klass getById(int id) throws DatabaseErrorException, NotFoundException;
    List<Klass> getAll() throws DatabaseErrorException, NotFoundException;
    Klass save(Klass klass) throws DatabaseErrorException;
    Klass delete(int id) throws DatabaseErrorException, NotFoundException;
}
