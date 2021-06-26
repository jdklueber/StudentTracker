package com.nofussprogramming.studentracker.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Klass {
    private Integer id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Roster> roster;
}
