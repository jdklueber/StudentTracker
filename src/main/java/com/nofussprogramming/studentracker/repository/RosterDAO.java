package com.nofussprogramming.studentracker.repository;

import com.nofussprogramming.studentracker.controller.exceptions.DatabaseErrorException;
import com.nofussprogramming.studentracker.controller.exceptions.NotFoundException;
import com.nofussprogramming.studentracker.model.Roster;

import java.util.List;

public interface RosterDAO {
    Roster getById(int id) throws DatabaseErrorException, NotFoundException;
    List<Roster> getAll() throws DatabaseErrorException, NotFoundException;
    List<Roster> getAllForKlass(int klassId) throws DatabaseErrorException, NotFoundException;
    Roster save(Roster roster) throws DatabaseErrorException;
    void saveAll(List<Roster> rosters) throws DatabaseErrorException;
    Roster delete(int id) throws DatabaseErrorException, NotFoundException;
    void deleteAllForKlass(int klassId) throws DatabaseErrorException;
}
