package com.nofussprogramming.studentracker.repository;

import com.nofussprogramming.studentracker.model.Log;

import java.util.List;

public interface LogDAO {
    Log getById(int id);
    List<Log> getAll();
    List<Log> getAllForStudent(int studentId);
    List<Log> getAllForKlass(int klassId);
    Log save(Log log);
    Log delete(int id);
}
