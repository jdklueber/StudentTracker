package com.nofussprogramming.studentracker.controller;

import com.nofussprogramming.studentracker.domain.KlassService;
import com.nofussprogramming.studentracker.domain.LogService;
import com.nofussprogramming.studentracker.model.Klass;
import com.nofussprogramming.studentracker.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/klass")
public class KlassController {
    final KlassService klassService;
    final LogService logService;

    @Autowired
    public KlassController(KlassService klassService, LogService logService) {
        this.klassService = klassService;
        this.logService = logService;
    }

    @GetMapping
    public List<Klass> getAll() {
        return klassService.getAll();
    }

    @GetMapping("/{id}")
    public Klass getById(@PathVariable int id) {
        return klassService.getById(id);
    }

    @PostMapping
    public Klass add(@RequestBody Klass klass) {
        return klassService.save(klass);
    }

    @PutMapping
    public void update(@RequestBody Klass klass) {
        klassService.save(klass);
    }

    @DeleteMapping("/{id}")
    public Klass delete(@PathVariable int id) {
        return klassService.delete(id);
    }

    @GetMapping("/{id}/logs")
    public List<Log> getLogs(@PathVariable int id) {
        return logService.getLogsForKlass(id);
    }
}
