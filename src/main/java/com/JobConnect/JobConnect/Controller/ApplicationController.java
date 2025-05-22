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

import com.JobConnect.JobConnect.Model.Application;
import com.JobConnect.JobConnect.Service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    
    @Autowired
    private ApplicationService applicationService;
    
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application savedApplication = applicationService.saveApplication(application);
        return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
    }
    
    @GetMapping("/{applicationId}")
    public ResponseEntity<Application> getApplicationById(@PathVariable UUID applicationId) {
        Optional<Application> application = applicationService.getApplicationById(applicationId);
        return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{applicationId}")
    public ResponseEntity<Application> updateApplication(@PathVariable UUID applicationId, @RequestBody Application applicationDetails) {
        Optional<Application> optionalApplication = applicationService.getApplicationById(applicationId);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            // Set only the fields that can be updated
            // Note: In this model, we might not have many fields to update besides relationships
            Application updatedApplication = applicationService.saveApplication(application);
            return new ResponseEntity<>(updatedApplication, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable UUID applicationId) {
        Optional<Application> application = applicationService.getApplicationById(applicationId);
        if (application.isPresent()) {
            applicationService.deleteApplication(applicationId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} 