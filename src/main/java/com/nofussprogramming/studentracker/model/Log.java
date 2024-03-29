package com.nofussprogramming.studentracker.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {
    private Integer id;
    private Integer klassId;
    private Integer studentId;
    private LocalDateTime timestamp;
    private Tag tag;
    private String body;
}
