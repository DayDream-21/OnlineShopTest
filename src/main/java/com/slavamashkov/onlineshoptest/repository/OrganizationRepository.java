package com.slavamashkov.onlineshoptest.repository;

import com.slavamashkov.onlineshoptest.entity.Organization;
import com.slavamashkov.onlineshoptest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findAllByUserAndActiveIsTrue(User user);

    List<Organization> findAllByUser(User user);
}
