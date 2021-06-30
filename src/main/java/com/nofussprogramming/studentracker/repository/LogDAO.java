package com.nofussprogramming.studentracker.repository;

import com.nofussprogramming.studentracker.controller.exceptions.DatabaseErrorException;
import com.nofussprogramming.studentracker.controller.exceptions.NotFoundException;
import com.nofussprogramming.studentracker.model.Log;

import java.util.List;

public interface LogDAO {
    Log getById(int id) throws DatabaseErrorException, NotFoundException;
    List<Log> getAll() throws DatabaseErrorException, NotFoundException;
    List<Log> getAllForStudent(int studentId) throws DatabaseErrorException, NotFoundException;
    List<Log> getAllForKlass(int klassId) throws DatabaseErrorException, NotFoundException;
    Log save(Log log) throws DatabaseErrorException;
    Log delete(int id) throws DatabaseErrorException, NotFoundException;
}
