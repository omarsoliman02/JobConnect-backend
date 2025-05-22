package com.JobConnect.JobConnect.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JobConnect.JobConnect.Model.Company;
import com.JobConnect.JobConnect.Model.Job;
import com.JobConnect.JobConnect.Service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    
    @Autowired
    private CompanyService companyService;
    
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company savedCompany = companyService.saveCompany(company);
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }
    
    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable UUID companyId) {
        Optional<Company> company = companyService.getCompanyById(companyId);
        return company.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{companyId}")
    public ResponseEntity<Company> updateCompany(@PathVariable UUID companyId, @RequestBody Company companyDetails) {
        Optional<Company> optionalCompany = companyService.getCompanyById(companyId);
        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            company.setName(companyDetails.getName());
            Company updatedCompany = companyService.saveCompany(company);
            return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable UUID companyId) {
        Optional<Company> company = companyService.getCompanyById(companyId);
        if (company.isPresent()) {
            companyService.deleteCompany(companyId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{companyId}/jobs")
    public ResponseEntity<List<Job>> getJobsByCompanyId(@PathVariable UUID companyId) {
        Optional<Company> company = companyService.getCompanyById(companyId);
        if (company.isPresent()) {
            List<Job> jobs = companyService.getJobsByCompanyId(companyId);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} 