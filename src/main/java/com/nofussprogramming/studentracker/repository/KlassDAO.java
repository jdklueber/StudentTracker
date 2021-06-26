package com.nofussprogramming.studentracker.repository;

import com.nofussprogramming.studentracker.model.Klass;

import java.util.List;

public interface KlassDAO {
    Klass getById(int id);
    List<Klass> getAll();
    Klass save(Klass klass);
    Klass delete(int id);
}
