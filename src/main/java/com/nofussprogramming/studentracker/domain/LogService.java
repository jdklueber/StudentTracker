package com.nofussprogramming.studentracker.domain;

import com.nofussprogramming.studentracker.model.Log;
import com.nofussprogramming.studentracker.repository.LogDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    final LogDAO logs;

    @Autowired
    public LogService(LogDAO logs) {
        this.logs = logs;
    }

    public List<Log> getLogsForStudent(Integer id) {
        return logs.getAllForStudent(id);
    }

    public List<Log> getLogsForKlass(Integer id) {
        return logs.getAllForKlass(id);
    }

    public Log save(Log log) {
        return logs.save(log);
    }

    public Log delete(int id) {
        return logs.delete(id);
    }
}
