package com.nofussprogramming.studentracker.controller;

import com.nofussprogramming.studentracker.domain.TagService;
import com.nofussprogramming.studentracker.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@CrossOrigin
@RequestMapping("/api/tags")
public class TagController {
    final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    Collection<Tag> getAll() {
        return tagService.getAllTags();
    }
}
