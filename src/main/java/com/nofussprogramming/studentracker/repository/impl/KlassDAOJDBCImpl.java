package com.nofussprogramming.studentracker.repository.impl;

import com.nofussprogramming.studentracker.controller.exceptions.DatabaseErrorException;
import com.nofussprogramming.studentracker.controller.exceptions.NotFoundException;
import com.nofussprogramming.studentracker.model.Klass;
import com.nofussprogramming.studentracker.repository.KlassDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
public class KlassDAOJDBCImpl implements KlassDAO {
    private final JdbcTemplate db;

    private static final String SQL_GETBYID = "select * from Klass where id = ?";
    private static final String SQL_GETALL = "select * from Klass";
    private static final String SQL_INSERT = "insert into Klass (name, start_date, end_date) values(?,?,?)";
    private static final String SQL_UPDATE = "update Klass set name = ?, start_date = ?, end_date = ? where id = ?";
    private static final String SQL_DELETE = "delete from Klass where id = ?";

    @Autowired
    public KlassDAOJDBCImpl(JdbcTemplate db) {
        this.db = db;
    }


    @Override
    public Klass getById(int id) {
        try {
            return db.queryForObject(SQL_GETBYID, new KlassRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Klass> getAll() {
        return db.query(SQL_GETALL, new KlassRowMapper());
    }

    @Override
    public Klass save(Klass klass) {
        if (klass.getId() != null) {
            return update(klass);
        } else {
            return add(klass);
        }
    }

    private Klass add(Klass klass){
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int rowsAffected = db.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, klass.getName());
                ps.setObject(2, klass.getStartDate());
                ps.setObject(3, klass.getEndDate());

                return ps;
            }, keyHolder);

            if (rowsAffected <= 0) {
                return null;
            }

            klass.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
            return klass;
        } catch (DataAccessException e) {
            throw new DatabaseErrorException();
        }
    }
    private Klass update(Klass klass) {
        try {
            db.update(SQL_UPDATE, klass.getName(), klass.getStartDate(), klass.getEndDate(), klass.getId());
            return klass;

        } catch (DataAccessException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public Klass delete(int id) {
        try {
            Klass klass = getById(id);
            if(klass != null) {
                db.update(SQL_DELETE, id);
                return klass;
            }
        } catch (DataAccessException e) {
            throw new DatabaseErrorException();
        }
        return null;
    }

    private static class KlassRowMapper implements RowMapper<Klass> {

        @Override
        public Klass mapRow(ResultSet row, int i) throws SQLException {
            Klass result = new Klass();

            result.setId(row.getInt("id"));
            result.setName(row.getString("name"));
            result.setStartDate(row.getDate("start_date").toLocalDate());
            result.setEndDate(row.getDate("end_date").toLocalDate());

            return result;
        }
    }
}
