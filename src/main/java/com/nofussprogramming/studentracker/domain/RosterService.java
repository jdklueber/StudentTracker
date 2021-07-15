package com.nofussprogramming.studentracker.domain;

import com.nofussprogramming.studentracker.model.Roster;
import com.nofussprogramming.studentracker.repository.RosterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RosterService {
    private final RosterDAO rosters;

    @Autowired
    public RosterService(RosterDAO rosters) {
        this.rosters = rosters;
    }

    public Roster updateRoster(Roster roster) {
        return rosters.save(roster);
    }
}
