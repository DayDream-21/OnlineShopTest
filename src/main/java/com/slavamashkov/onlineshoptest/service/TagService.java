package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Tag;

import java.util.List;

public interface TagService {
    void save(Tag tag);
    List<Tag> findAllTags();
}
