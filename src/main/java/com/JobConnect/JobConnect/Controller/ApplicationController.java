package com.JobConnect.JobConnect.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.JobConnect.JobConnect.Model.Application;
import com.JobConnect.JobConnect.Service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    
    @Autowired
    private ApplicationService applicationService;
    
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        logger.info("Getting all applications");
        List<Application> applications = applicationService.getAllApplications();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        logger.info("Received application creation request: {}", application);
        
        // Validate the input
        if (application.getApplicant() == null || application.getApplicant().getId() == null) {
            logger.error("Applicant or applicant ID is missing");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        if (application.getJob() == null || application.getJob().getId() == null) {
            logger.error("Job or job ID is missing");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        try {
            logger.info("Saving application for applicant {} and job {}", 
                        application.getApplicant().getId(), 
                        application.getJob().getId());
                        
            Application savedApplication = applicationService.saveApplication(application);
            logger.info("Application saved successfully with ID: {}", savedApplication.getId());
            return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving application", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{applicationId}")
    public ResponseEntity<Application> getApplicationById(@PathVariable UUID applicationId) {
        logger.info("Getting application by ID: {}", applicationId);
        Optional<Application> application = applicationService.getApplicationById(applicationId);
        return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{applicationId}")
    public ResponseEntity<Application> updateApplication(@PathVariable UUID applicationId, @RequestBody Application applicationDetails) {
        logger.info("Updating application ID: {}", applicationId);
        Optional<Application> optionalApplication = applicationService.getApplicationById(applicationId);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            // Set only the fields that can be updated
            // Note: In this model, we might not have many fields to update besides relationships
            Application updatedApplication = applicationService.saveApplication(application);
            logger.info("Application updated successfully");
            return new ResponseEntity<>(updatedApplication, HttpStatus.OK);
        } else {
            logger.warn("Application not found with ID: {}", applicationId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable UUID applicationId) {
        logger.info("Deleting application ID: {}", applicationId);
        Optional<Application> application = applicationService.getApplicationById(applicationId);
        if (application.isPresent()) {
            applicationService.deleteApplication(applicationId);
            logger.info("Application deleted successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.warn("Application not found with ID: {}", applicationId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} 