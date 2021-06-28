package com.nofussprogramming.studentracker.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Klass {
    private Integer id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
