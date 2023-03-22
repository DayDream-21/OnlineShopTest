package com.slavamashkov.onlineshoptest.service.implementation;

import com.slavamashkov.onlineshoptest.entity.Tag;
import com.slavamashkov.onlineshoptest.repository.TagRepository;
import com.slavamashkov.onlineshoptest.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Set<Tag> getAllTagsByIds(List<Long> tagsId) {
        return new HashSet<>(tagRepository.findAllById(tagsId));
    }
}
