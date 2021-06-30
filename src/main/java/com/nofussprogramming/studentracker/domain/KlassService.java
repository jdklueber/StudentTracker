package com.nofussprogramming.studentracker.domain;

import com.nofussprogramming.studentracker.model.Klass;
import com.nofussprogramming.studentracker.repository.KlassDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KlassService {
    final KlassDAO klasses;

    @Autowired
    public KlassService(KlassDAO klasses) {
        this.klasses = klasses;
    }

    public List<Klass> getAll() {
        return klasses.getAll();
    }

    public Klass getById(Integer id) {
        return klasses.getById(id);
    }

    public Klass save(Klass klass) {
        return klasses.save(klass);
    }

    public Klass delete(int id) {
        return klasses.delete(id);
    }
}
