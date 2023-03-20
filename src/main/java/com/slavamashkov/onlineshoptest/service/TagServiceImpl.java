package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Tag;
import com.slavamashkov.onlineshoptest.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public void save(Tag tagDto) {
        Tag tag = tagRepository.findByName(tagDto.getName()).orElse(null);

        if (tag != null) {
            tag.setName(tagDto.getName());
        } else {
            tag = Tag.builder()
                    .name(tagDto.getName())
                    .build();
        }

        tagRepository.save(tag);
    }

    @Override
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }
}
