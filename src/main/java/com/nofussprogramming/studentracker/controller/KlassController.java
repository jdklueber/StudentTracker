package com.nofussprogramming.studentracker.controller;

import com.nofussprogramming.studentracker.domain.KlassService;
import com.nofussprogramming.studentracker.model.Klass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/klass")
public class KlassController {
    final KlassService klassService;

    @Autowired
    public KlassController(KlassService klassService) {
        this.klassService = klassService;
    }

    @GetMapping
    public List<Klass> getAll() {
        return klassService.getAll();
    }

    @GetMapping("/{id}")
    public Klass getById(@PathVariable int id) {
        return klassService.getById(id);
    }


}
