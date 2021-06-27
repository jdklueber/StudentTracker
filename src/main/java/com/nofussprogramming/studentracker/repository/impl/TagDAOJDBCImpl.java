package com.nofussprogramming.studentracker.repository.impl;

import com.nofussprogramming.studentracker.model.Tag;
import com.nofussprogramming.studentracker.repository.TagDAO;
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
public class TagDAOJDBCImpl implements TagDAO {
    private final JdbcTemplate db;

    private static final String SQL_GETBYID = "select * from Tag where id = ?";
    private static final String SQL_GETALL = "select * from Tag";
    private static final String SQL_INSERT = "insert into Tag (tag) values(?)";
    private static final String SQL_UPDATE = "update Tag set tag = ? where id = ?";
    private static final String SQL_DELETE = "delete from Tag where id = ?";

    @Autowired
    public TagDAOJDBCImpl(JdbcTemplate db) {
        this.db = db;
    }

    @Override
    public Tag getById(int id) {
        try {
            return db.queryForObject(SQL_GETBYID, new TagRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Tag> getAll() {
        return db.query(SQL_GETALL, new TagRowMapper());
    }

    @Override
    public Tag save(Tag tag) {
        if (tag.getId() != null) {
            return update(tag);
        } else {
            return add(tag);
        }
    }

    private Tag add(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = db.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getTag());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        tag.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return tag;
    }

    private Tag update(Tag tag) {
        db.update(SQL_UPDATE, tag.getTag(), tag.getId());
        return tag;
    }

    @Override
    public Tag delete(int id) {
        Tag target = getById(id);
        if (target != null) {
            db.update(SQL_DELETE, id);
            return target;
        } else {
            return null;
        }
    }

    private static class TagRowMapper implements RowMapper<Tag> {

        @Override
        public Tag mapRow(ResultSet row, int i) throws SQLException {
            Tag result = new Tag();

            result.setId(row.getInt("id"));
            result.setTag(row.getString("tag"));

            return result;
        }
    }
}
