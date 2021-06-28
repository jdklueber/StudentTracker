package com.nofussprogramming.studentracker.repository.impl;

import com.nofussprogramming.studentracker.model.Roster;
import com.nofussprogramming.studentracker.repository.RosterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class RosterDAOJDBCImpl implements RosterDAO {
    private final JdbcTemplate db;

    private static final String SQL_GETBYID = "select * from Roster where id = ?";
    private static final String SQL_GETBYKLASS = "select * from Roster where klass_id = ?";
    private static final String SQL_GETBYSTUDENT = "select * from Roster where student_id = ?";
    private static final String SQL_GETALL = "select * from Roster";
    private static final String SQL_INSERT = "insert into Roster (klass_id, student_id, is_active) values(?,?,?)";
    private static final String SQL_UPDATE = "update Roster set klass_id = ?, student_id = ?, is_active = ? where id = ?";
    private static final String SQL_DELETE = "delete from Roster where id = ?";

    @Autowired
    public RosterDAOJDBCImpl(JdbcTemplate db) {
        this.db = db;
    }

    @Override
    public Roster getById(int id) {
        try {
            return db.queryForObject(SQL_GETBYID, new RosterRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Roster> getAll() {
        return db.query(SQL_GETALL, new RosterRowMapper());
    }

    @Override
    public List<Roster> getAllForKlass(int klassId) {
        return db.query(SQL_GETBYKLASS, new RosterRowMapper(), klassId);
    }

    @Override
    public List<Roster> getAllForStudent(int studentId) {
        return db.query(SQL_GETBYSTUDENT, new RosterRowMapper(), studentId);
    }

    @Override
    public Roster save(Roster roster) {
        if(roster.getId() != null) {
            return update(roster);
        } else {
            return add(roster);
        }
    }

    private Roster add(Roster roster){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = db.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, roster.getKlassId());
            ps.setInt(2, roster.getStudentId());
            ps.setBoolean(3, roster.isActive());

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        roster.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return roster;
    }
    private Roster update(Roster roster) {
        db.update(SQL_UPDATE, roster.getKlassId(), roster.getStudentId(), roster.isActive(), roster.getId());
        return roster;
    }

    @Override
    public void saveAll(List<Roster> rosters) {
        for (Roster r : rosters) {
            save(r);
        }
    }

    @Override
    public Roster delete(int id) {
        Roster r = getById(id);
        if (r != null) {
            db.update(SQL_DELETE, id);
        }
        return r;
    }

    private static class RosterRowMapper implements RowMapper<Roster> {

        @Override
        public Roster mapRow(ResultSet row, int i) throws SQLException {
            Roster result = new Roster();

            result.setId(row.getInt("id"));
            result.setKlassId(row.getInt("klass_id"));
            result.setStudentId(row.getInt("student_id"));
            result.setActive(row.getBoolean("is_active"));

            return result;
        }
    }
}
