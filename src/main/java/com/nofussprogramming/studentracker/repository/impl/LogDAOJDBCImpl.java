package com.nofussprogramming.studentracker.repository.impl;

import com.nofussprogramming.studentracker.controller.exceptions.DatabaseErrorException;
import com.nofussprogramming.studentracker.controller.exceptions.NotFoundException;
import com.nofussprogramming.studentracker.model.Log;
import com.nofussprogramming.studentracker.repository.LogDAO;
import com.nofussprogramming.studentracker.repository.TagDAO;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class LogDAOJDBCImpl implements LogDAO {
    private final JdbcTemplate db;
    private final TagDAO tags;

    private static final String SQL_GETBYID = "select * from Log where id = ?";
    private static final String SQL_GETBYKLASS = "select * from Log where klass_id = ?";
    private static final String SQL_GETBYSTUDENT = "select * from Log where student_id = ?";
    private static final String SQL_GETALL = "select * from Log";
    private static final String SQL_INSERT = "insert into Log (klass_id, student_id, time_stamp, tag_id, body) values(?,?,?,?,?)";
    private static final String SQL_UPDATE = "update Log set klass_id = ?, student_id = ?, time_stamp = ?, tag_id = ?, body = ? where id = ?";
    private static final String SQL_DELETE = "delete from Log where id = ?";

    @Autowired
    public LogDAOJDBCImpl(JdbcTemplate db, TagDAO tags) {
        this.db = db;
        this.tags = tags;
    }

    @Override
    public Log getById(int id) {
        try {
            return db.queryForObject(SQL_GETBYID, new LogRowMapper(tags), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Log> getAll() {
        return db.query(SQL_GETALL, new LogRowMapper(tags));
    }

    @Override
    public List<Log> getAllForStudent(int studentId) {
        return db.query(SQL_GETBYSTUDENT, new LogRowMapper(tags), studentId);
    }

    @Override
    public List<Log> getAllForKlass(int klassId) {
        return db.query(SQL_GETBYKLASS, new LogRowMapper(tags), klassId);
    }

    @Override
    public Log save(Log log) {
        log.setTimestamp(LocalDateTime.now());  //Timestamp is always last updated time
        if (log.getId() != null) {
            return update(log);
        } else {
            return add(log);
        }
    }

    private Log add(Log log) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int rowsAffected = db.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, log.getKlassId());
                ps.setInt(2, log.getStudentId());
                ps.setObject(3, log.getTimestamp());
                ps.setInt(4, log.getTag().getId());
                ps.setString(5, log.getBody());

                return ps;
            }, keyHolder);

            if (rowsAffected <= 0) {
                return null;
            }

            log.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
            return log;

        } catch (DataAccessException e) {
            throw new DatabaseErrorException();
        }
    }

    private Log update(Log log) {
        try {
            db.update(SQL_UPDATE, log.getKlassId(), log.getStudentId(), log.getTimestamp(), log.getTag().getId(), log.getBody(), log.getId());
            return log;
        } catch (DataAccessException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public Log delete(int id) {
        try {
            Log l = getById(id);
            if (l != null) {
                db.update(SQL_DELETE, l.getId());
            } else {
                throw new NotFoundException();
            }
            return l;
        } catch (DataAccessException e) {
            throw new DatabaseErrorException();
        }
    }

    private static class LogRowMapper implements RowMapper<Log> {
        final TagDAO tags;

        public LogRowMapper(TagDAO tags) {
            this.tags = tags;
        }

        @Override
        public Log mapRow(ResultSet row, int i) throws SQLException {
            Log result = new Log();

            result.setId(row.getInt("id"));
            result.setKlassId(row.getInt("klass_id"));
            result.setStudentId(row.getInt("student_id"));
            result.setTimestamp(row.getTimestamp("time_stamp").toLocalDateTime());
            result.setBody(row.getString("body"));

            result.setTag(tags.getById(row.getInt("tag_id")));

            return result;
        }
    }
}
