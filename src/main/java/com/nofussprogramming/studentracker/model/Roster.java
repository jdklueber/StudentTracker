package com.nofussprogramming.studentracker.model;

import lombok.Data;

@Data
public class Roster {
    private Integer id;
    private Integer classId;
    private Integer studentId;
    private Student student;
    private boolean isActive;
}
