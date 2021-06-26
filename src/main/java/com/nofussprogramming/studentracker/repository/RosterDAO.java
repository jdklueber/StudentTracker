package com.nofussprogramming.studentracker.repository;

import com.nofussprogramming.studentracker.model.Roster;

import java.util.List;

public interface RosterDAO {
    Roster getById(int id);
    List<Roster> getAll();
    List<Roster> getAllForKlass(int klassId);
    Roster save(Roster roster);
    void saveAll(List<Roster> rosters);
    Roster delete(int id);
}
