package com.JobConnect.JobConnect.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobConnect.JobConnect.Model.Company;
import com.JobConnect.JobConnect.Model.Job;
import com.JobConnect.JobConnect.Repository.CompanyRepository;
import com.JobConnect.JobConnect.Repository.JobRepository;

@Service
public class CompanyService {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private JobRepository jobRepository;
    
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
    
    public Optional<Company> getCompanyById(UUID id) {
        return companyRepository.findById(id);
    }
    
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }
    
    public void deleteCompany(UUID id) {
        companyRepository.deleteById(id);
    }
    
    public List<Job> getJobsByCompanyId(UUID companyId) {
        return jobRepository.findByCompanyId(companyId);
    }
    
    public List<Company> searchCompaniesByName(String searchTerm) {
        // Convert search term to lowercase for case-insensitive comparison
        String searchTermLower = searchTerm.toLowerCase();
        
        // Get all companies and filter by name containing the search term
        return companyRepository.findAll().stream()
                .filter(company -> company.getName().toLowerCase().contains(searchTermLower))
                .collect(Collectors.toList());
    }
} 