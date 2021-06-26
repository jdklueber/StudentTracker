package com.nofussprogramming.studentracker.repository;

import com.nofussprogramming.studentracker.model.Tag;

import java.util.List;

public interface TagDAO {
    Tag getById(int id);
    List<Tag> getAll();
    Tag save(Tag tag);
    Tag delete(int id);
}
