package com.JobConnect.JobConnect.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

import com.JobConnect.JobConnect.Model.Application;
import com.JobConnect.JobConnect.Model.Company;
import com.JobConnect.JobConnect.Model.ExperienceLevel;
import com.JobConnect.JobConnect.Model.Job;
import com.JobConnect.JobConnect.Model.JobType;
import com.JobConnect.JobConnect.Service.CompanyService;
import com.JobConnect.JobConnect.Service.JobService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/jobs", 
                produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Job", description = "Job management APIs")
public class JobController {
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private CompanyService companyService;
    
    @GetMapping
    @Operation(summary = "Get all jobs", description = "Retrieve a list of all jobs")
    @ApiResponse(responseCode = "200", description = "Jobs found", 
                 content = @Content(schema = @Schema(implementation = Job.class)))
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }
    
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @Operation(summary = "Create a job", description = "Add a new job to the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Job created", 
                     content = @Content(schema = @Schema(implementation = Job.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<Job> createJob(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Job object that needs to be created",
                required = true,
                content = @Content(schema = @Schema(implementation = Job.class))
            )
            @RequestBody Job job) {
        Job savedJob = jobService.saveJob(job);
        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }
    
    @GetMapping("/{jobId}")
    @Operation(summary = "Get job by ID", description = "Retrieve a specific job by its UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Job found", 
                     content = @Content(schema = @Schema(implementation = Job.class))),
        @ApiResponse(responseCode = "404", description = "Job not found", content = @Content)
    })
    public ResponseEntity<Job> getJobById(
            @Parameter(description = "UUID of the job to retrieve", required = true)
            @PathVariable UUID jobId) {
        Optional<Job> job = jobService.getJobById(jobId);
        
        // If job exists but company is null, try to fetch company data
        if (job.isPresent() && job.get().getCompany() == null) {
            // Check if company_id exists in database but wasn't loaded
            UUID companyId = jobService.getCompanyIdForJob(jobId);
            if (companyId != null) {
                Optional<Company> company = companyService.getCompanyById(companyId);
                company.ifPresent(c -> job.get().setCompany(c));
            }
        }
        
        return job.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(path = "/{jobId}", consumes = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @Operation(summary = "Update job", description = "Update an existing job by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Job updated", 
                     content = @Content(schema = @Schema(implementation = Job.class))),
        @ApiResponse(responseCode = "404", description = "Job not found", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<Job> updateJob(
            @Parameter(description = "UUID of the job to update", required = true)
            @PathVariable UUID jobId, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated job object",
                required = true,
                content = @Content(schema = @Schema(implementation = Job.class))
            )
            @RequestBody Job jobDetails) {
        Optional<Job> optionalJob = jobService.getJobById(jobId);
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();
            job.setTitle(jobDetails.getTitle());
            job.setDescription(jobDetails.getDescription());
            job.setLocation(jobDetails.getLocation());
            job.setJobType(jobDetails.getJobType());
            job.setMinSalary(jobDetails.getMinSalary());
            job.setMaxSalary(jobDetails.getMaxSalary());
            job.setExperienceLevel(jobDetails.getExperienceLevel());
            Job updatedJob = jobService.saveJob(job);
            return new ResponseEntity<>(updatedJob, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{jobId}")
    @Operation(summary = "Delete job", description = "Delete a job by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Job deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "Job not found", content = @Content)
    })
    public ResponseEntity<Void> deleteJob(
            @Parameter(description = "UUID of the job to delete", required = true)
            @PathVariable UUID jobId) {
        Optional<Job> job = jobService.getJobById(jobId);
        if (job.isPresent()) {
            jobService.deleteJob(jobId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{jobId}/applications")
    @Operation(summary = "Get job applications", description = "Retrieve all applications for a specific job")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Applications found", 
                     content = @Content(schema = @Schema(implementation = Application.class))),
        @ApiResponse(responseCode = "404", description = "Job not found", content = @Content)
    })
    public ResponseEntity<List<Application>> getApplicationsByJobId(
            @Parameter(description = "UUID of the job to retrieve applications for", required = true)
            @PathVariable UUID jobId) {
        Optional<Job> job = jobService.getJobById(jobId);
        if (job.isPresent()) {
            List<Application> applications = jobService.getApplicationsByJobId(jobId);
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search jobs", description = "Search and filter jobs by various criteria")
    @ApiResponse(responseCode = "200", description = "Search results", 
                 content = @Content(schema = @Schema(implementation = Job.class)))
    public ResponseEntity<List<Job>> searchJobs(
            @Parameter(description = "Location to filter by", required = false)
            @RequestParam(required = false) String location,
            
            @Parameter(description = "Job type to filter by", required = false)
            @RequestParam(required = false) JobType jobType,
            
            @Parameter(description = "Minimum salary to filter by", required = false)
            @RequestParam(required = false) Integer salaryMin,
            
            @Parameter(description = "Maximum salary to filter by", required = false)
            @RequestParam(required = false) Integer salaryMax,
            
            @Parameter(description = "Experience level to filter by", required = false)
            @RequestParam(required = false) ExperienceLevel experienceLevel) {
        
        List<Job> allJobs = jobService.getAllJobs();
        List<Job> filteredJobs = allJobs.stream()
            .filter(job -> {
                // Filter by location if provided
                if (location != null && !location.isEmpty() && job.getLocation() != null) {
                    if (!job.getLocation().toLowerCase().contains(location.toLowerCase())) {
                        return false;
                    }
                }
                
                // Filter by job type if provided
                if (jobType != null && job.getJobType() != null) {
                    if (job.getJobType() != jobType) {
                        return false;
                    }
                }
                
                // Filter by minimum salary if provided
                if (salaryMin != null && job.getMinSalary() != null) {
                    if (job.getMinSalary() < salaryMin) {
                        return false;
                    }
                }
                
                // Filter by maximum salary if provided
                if (salaryMax != null && job.getMaxSalary() != null) {
                    if (job.getMaxSalary() > salaryMax) {
                        return false;
                    }
                }
                
                // Filter by experience level if provided
                if (experienceLevel != null && job.getExperienceLevel() != null) {
                    if (job.getExperienceLevel() != experienceLevel) {
                        return false;
                    }
                }
                
                return true;
            })
            .collect(Collectors.toList());
        
        return new ResponseEntity<>(filteredJobs, HttpStatus.OK);
    }
} 