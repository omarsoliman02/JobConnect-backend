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

import com.JobConnect.JobConnect.Model.Applicant;
import com.JobConnect.JobConnect.Model.Application;
import com.JobConnect.JobConnect.Model.Experience;
import com.JobConnect.JobConnect.Service.ApplicantService;

@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {
    
    @Autowired
    private ApplicantService applicantService;
    
    @GetMapping
    public ResponseEntity<List<Applicant>> getAllApplicants() {
        List<Applicant> applicants = applicantService.getAllApplicants();
        return new ResponseEntity<>(applicants, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant) {
        Applicant savedApplicant = applicantService.saveApplicant(applicant);
        return new ResponseEntity<>(savedApplicant, HttpStatus.CREATED);
    }
    
    @GetMapping("/{applicantId}")
    public ResponseEntity<Applicant> getApplicantById(@PathVariable UUID applicantId) {
        Optional<Applicant> applicant = applicantService.getApplicantById(applicantId);
        return applicant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{applicantId}")
    public ResponseEntity<Applicant> updateApplicant(@PathVariable UUID applicantId, @RequestBody Applicant applicantDetails) {
        Optional<Applicant> optionalApplicant = applicantService.getApplicantById(applicantId);
        if (optionalApplicant.isPresent()) {
            Applicant applicant = optionalApplicant.get();
            applicant.setFirstName(applicantDetails.getFirstName());
            applicant.setLastName(applicantDetails.getLastName());
            applicant.setEmail(applicantDetails.getEmail());
            applicant.setPhone(applicantDetails.getPhone());
            Applicant updatedApplicant = applicantService.saveApplicant(applicant);
            return new ResponseEntity<>(updatedApplicant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{applicantId}")
    public ResponseEntity<Void> deleteApplicant(@PathVariable UUID applicantId) {
        Optional<Applicant> applicant = applicantService.getApplicantById(applicantId);
        if (applicant.isPresent()) {
            applicantService.deleteApplicant(applicantId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{applicantId}/applications")
    public ResponseEntity<List<Application>> getApplicationsByApplicantId(@PathVariable UUID applicantId) {
        Optional<Applicant> applicant = applicantService.getApplicantById(applicantId);
        if (applicant.isPresent()) {
            List<Application> applications = applicantService.getApplicationsByApplicantId(applicantId);
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{applicantId}/experiences")
    public ResponseEntity<List<Experience>> getExperiencesByApplicantId(@PathVariable UUID applicantId) {
        Optional<Applicant> applicant = applicantService.getApplicantById(applicantId);
        if (applicant.isPresent()) {
            List<Experience> experiences = applicantService.getExperiencesByApplicantId(applicantId);
            return new ResponseEntity<>(experiences, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} 