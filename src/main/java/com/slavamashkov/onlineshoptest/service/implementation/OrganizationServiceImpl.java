package com.slavamashkov.onlineshoptest.service.implementation;

import com.slavamashkov.onlineshoptest.entity.Organization;
import com.slavamashkov.onlineshoptest.entity.User;
import com.slavamashkov.onlineshoptest.repository.OrganizationRepository;
import com.slavamashkov.onlineshoptest.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Override
    public List<Organization> getAllOrganizationsByUserAndActiveIsTrue(User user) {
        return organizationRepository.findAllByUserAndActiveIsTrue(user);
    }

    @Override
    public List<Organization> getAllOrganizationsByUser(User user) {
        return organizationRepository.findAllByUser(user);
    }

    @Override
    public Organization getOrganizationById(Long id) {
        return organizationRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such organization with id: " + id));
    }

    @Override
    public void save(Organization organization) {
        organizationRepository.save(organization);
    }

    @Override
    public List<Organization> getAll() {
        return organizationRepository.findAll();
    }
}
