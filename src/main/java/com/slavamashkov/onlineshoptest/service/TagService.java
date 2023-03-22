package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    void save(Tag tag);
    List<Tag> getAllTags();
    Set<Tag> getAllTagsByIds(List<Long> tagsId);
}
