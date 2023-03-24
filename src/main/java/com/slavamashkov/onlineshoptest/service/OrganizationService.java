package com.slavamashkov.onlineshoptest.service;

import com.slavamashkov.onlineshoptest.entity.Organization;
import com.slavamashkov.onlineshoptest.entity.User;

import java.util.List;

public interface OrganizationService {
    List<Organization> getAllOrganizationsByUserAndActiveIsTrue(User user);
    List<Organization> getAllOrganizationsByUser(User user);
    Organization getOrganizationById(Long id);
    void save(Organization organization);

    List<Organization> getAll();
}
