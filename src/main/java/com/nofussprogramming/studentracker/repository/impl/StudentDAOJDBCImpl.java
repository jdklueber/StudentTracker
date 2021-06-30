package com.nofussprogramming.studentracker.repository.impl;

import com.nofussprogramming.studentracker.controller.exceptions.DatabaseErrorException;
import com.nofussprogramming.studentracker.controller.exceptions.NotFoundException;
import com.nofussprogramming.studentracker.model.Student;
import com.nofussprogramming.studentracker.repository.StudentDAO;
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
public class StudentDAOJDBCImpl implements StudentDAO {
    private final JdbcTemplate db;

    private static final String SQL_GETBYID = "select * from Student where id = ?";
    private static final String SQL_GETALL = "select * from Student";
    private static final String SQL_INSERT = "insert into Student (first_name, last_name) values(?,?)";
    private static final String SQL_UPDATE = "update Student set first_name = ?, last_name = ? where id = ?";
    private static final String SQL_DELETE = "delete from Student where id = ?";

    @Autowired
    public StudentDAOJDBCImpl(JdbcTemplate db) {
        this.db = db;
    }

    @Override
    public Student getById(int id) {
        try {
            return db.queryForObject(SQL_GETBYID, new StudentRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Student> getAll() {
        return db.query(SQL_GETALL, new StudentRowMapper());
    }

    @Override
    public Student save(Student student) {
        if (student.getId() != null) {
            return update(student);
        } else {
            return add(student);
        }
    }

    private Student add(Student student){
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int rowsAffected = db.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, student.getFirstName());
                ps.setString(2, student.getLastName());
                return ps;
            }, keyHolder);

            if (rowsAffected <= 0) {
                return null;
            }

            student.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
            return student;
        } catch (DataAccessException e) {
            throw new DatabaseErrorException();
        }
    }
    private Student update(Student student) {
        try {
            db.update(SQL_UPDATE, student.getFirstName(), student.getLastName(), student.getId());
            return student;
        } catch (DataAccessException e) {
            throw new DatabaseErrorException();
        }
    }


    @Override
    public Student delete(int id) {
        Student student = getById(id);
        if (student != null) {
            try {
                db.update(SQL_DELETE, id);
            } catch (DataAccessException e) {
                throw new DatabaseErrorException();
            }
        } else {
            throw new NotFoundException();
        }

        return student;
    }

    private static class StudentRowMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet row, int i) throws SQLException {
            Student result = new Student();

            result.setId(row.getInt("id"));
            result.setFirstName(row.getString("first_name"));
            result.setLastName(row.getString("last_name"));

            return result;
        }
    }
}
