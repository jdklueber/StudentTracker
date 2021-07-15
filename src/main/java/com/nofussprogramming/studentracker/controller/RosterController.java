package com.nofussprogramming.studentracker.controller;

import com.nofussprogramming.studentracker.domain.RosterService;
import com.nofussprogramming.studentracker.model.Roster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/roster")
public class RosterController {
    private final RosterService rosterService;

    @Autowired
    public RosterController(RosterService rosterService) {
        this.rosterService = rosterService;
    }

    @PutMapping
    public Roster updateRoster(@RequestBody Roster roster) {
        roster = rosterService.updateRoster(roster);

        return roster;
    }
}
