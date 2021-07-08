package com.nofussprogramming.studentracker.controller;

import com.nofussprogramming.studentracker.domain.LogService;
import com.nofussprogramming.studentracker.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/log")
public class LogController {
    LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public Log add(@RequestBody Log log) {
        return logService.save(log);
    }

    @PutMapping
    public void update(@RequestBody Log log) {
        logService.save(log);
    }

    @DeleteMapping("/{id}")
    public Log delete(@PathVariable int id) {
        return logService.delete(id);
    }
}
