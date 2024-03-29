package com.nofussprogramming.studentracker.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Klass {
    private Integer id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    List<Roster> roster = new ArrayList<>();
}
