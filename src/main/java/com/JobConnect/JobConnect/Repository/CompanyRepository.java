package com.JobConnect.JobConnect.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JobConnect.JobConnect.Model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
} 