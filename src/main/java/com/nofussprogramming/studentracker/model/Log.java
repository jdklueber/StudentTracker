package com.nofussprogramming.studentracker.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {
    private Integer id;
    private Integer classId;
    private Integer studentId;
    private LocalDateTime timestamp;
    private String tag;
    private String body;
}
