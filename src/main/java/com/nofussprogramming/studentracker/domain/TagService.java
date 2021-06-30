package com.nofussprogramming.studentracker.domain;

import com.nofussprogramming.studentracker.model.Tag;
import com.nofussprogramming.studentracker.repository.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TagService {
    TagDAO tags;

    @Autowired
    public TagService(TagDAO tags) {
        this.tags = tags;
    }

    public Collection<Tag> getAllTags() {
        return tags.getAll();
    }
}
