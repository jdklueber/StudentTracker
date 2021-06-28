package com.nofussprogramming.studentracker.model;

import lombok.Data;

@Data
public class Roster {
    private Integer id;
    private Integer klassId;
    private Integer studentId;
    private boolean isActive;
}
