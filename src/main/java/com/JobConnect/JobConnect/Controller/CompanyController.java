package com.JobConnect.JobConnect.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JobConnect.JobConnect.Model.Company;
import com.JobConnect.JobConnect.Model.Job;
import com.JobConnect.JobConnect.Service.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/companies", 
                produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Company", description = "Company management APIs")
public class CompanyController {
    
    @Autowired
    private CompanyService companyService;
    
    @GetMapping
    @Operation(summary = "Get all companies", description = "Retrieve a list of all companies")
    @ApiResponse(responseCode = "200", description = "Companies found", 
                 content = @Content(schema = @Schema(implementation = Company.class)))
    public ResponseEntity<List<Company>> getAllCompanies(
            @Parameter(description = "Optional search term to filter companies by name", required = false)
            @RequestParam(required = false) String search) {
        
        if (search != null && !search.isEmpty()) {
            List<Company> matchingCompanies = companyService.searchCompaniesByName(search);
            return new ResponseEntity<>(matchingCompanies, HttpStatus.OK);
        }
        
        List<Company> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }
    
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @Operation(summary = "Create a company", description = "Add a new company to the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Company created", 
                     content = @Content(schema = @Schema(implementation = Company.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<Company> createCompany(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Company object that needs to be created",
                required = true,
                content = @Content(schema = @Schema(implementation = Company.class))
            )
            @RequestBody Company company) {
        Company savedCompany = companyService.saveCompany(company);
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }
    
    @GetMapping("/{companyId}")
    @Operation(summary = "Get company by ID", description = "Retrieve a specific company by its UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Company found", 
                     content = @Content(schema = @Schema(implementation = Company.class))),
        @ApiResponse(responseCode = "404", description = "Company not found", content = @Content)
    })
    public ResponseEntity<Company> getCompanyById(
            @Parameter(description = "UUID of the company to retrieve", required = true)
            @PathVariable UUID companyId) {
        Optional<Company> company = companyService.getCompanyById(companyId);
        return company.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(path = "/{companyId}", consumes = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @Operation(summary = "Update company", description = "Update an existing company by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Company updated", 
                     content = @Content(schema = @Schema(implementation = Company.class))),
        @ApiResponse(responseCode = "404", description = "Company not found", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<Company> updateCompany(
            @Parameter(description = "UUID of the company to update", required = true)
            @PathVariable UUID companyId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated company object",
                required = true,
                content = @Content(schema = @Schema(implementation = Company.class))
            ) 
            @RequestBody Company companyDetails) {
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
    @Operation(summary = "Delete company", description = "Delete a company by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Company deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "Company not found", content = @Content)
    })
    public ResponseEntity<Void> deleteCompany(
            @Parameter(description = "UUID of the company to delete", required = true)
            @PathVariable UUID companyId) {
        Optional<Company> company = companyService.getCompanyById(companyId);
        if (company.isPresent()) {
            companyService.deleteCompany(companyId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{companyId}/jobs")
    @Operation(summary = "Get company jobs", description = "Retrieve all jobs for a specific company")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jobs found", 
                     content = @Content(schema = @Schema(implementation = Job.class))),
        @ApiResponse(responseCode = "404", description = "Company not found", content = @Content)
    })
    public ResponseEntity<List<Job>> getJobsByCompanyId(
            @Parameter(description = "UUID of the company to retrieve jobs for", required = true)
            @PathVariable UUID companyId) {
        Optional<Company> company = companyService.getCompanyById(companyId);
        if (company.isPresent()) {
            List<Job> jobs = companyService.getJobsByCompanyId(companyId);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} 