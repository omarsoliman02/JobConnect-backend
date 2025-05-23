package com.JobConnect.JobConnect.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.JobConnect.JobConnect.Model.Applicant;
import com.JobConnect.JobConnect.Model.Application;
import com.JobConnect.JobConnect.Model.Job;
import com.JobConnect.JobConnect.Repository.ApplicantRepository;
import com.JobConnect.JobConnect.Repository.ApplicationRepository;
import com.JobConnect.JobConnect.Repository.JobRepository;

@Service
public class ApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private ApplicantRepository applicantRepository;
    
    @Autowired
    private JobRepository jobRepository;
    
    public List<Application> getAllApplications() {
        logger.info("Getting all applications");
        return applicationRepository.findAll();
    }
    
    public Optional<Application> getApplicationById(UUID id) {
        logger.info("Getting application by ID: {}", id);
        return applicationRepository.findById(id);
    }
    
    @Transactional
    public Application saveApplication(Application application) {
        logger.info("Saving application: {}", application);
        
        // Make sure we have valid references to Applicant and Job
        if (application.getApplicant() != null && application.getApplicant().getId() != null) {
            UUID applicantId = application.getApplicant().getId();
            
            Optional<Applicant> applicantOpt = applicantRepository.findById(applicantId);
            if (!applicantOpt.isPresent()) {
                logger.error("Applicant with ID {} not found", applicantId);
                throw new RuntimeException("Applicant not found with ID: " + applicantId);
            }
            
            // Set the complete applicant entity
            application.setApplicant(applicantOpt.get());
            logger.info("Found and set applicant: {}", applicantOpt.get().getId());
        } else {
            logger.error("Application has no valid applicant reference");
            throw new RuntimeException("Application must have a valid applicant reference");
        }
        
        if (application.getJob() != null && application.getJob().getId() != null) {
            UUID jobId = application.getJob().getId();
            
            Optional<Job> jobOpt = jobRepository.findById(jobId);
            if (!jobOpt.isPresent()) {
                logger.error("Job with ID {} not found", jobId);
                throw new RuntimeException("Job not found with ID: " + jobId);
            }
            
            // Set the complete job entity
            application.setJob(jobOpt.get());
            logger.info("Found and set job: {}", jobOpt.get().getId());
        } else {
            logger.error("Application has no valid job reference");
            throw new RuntimeException("Application must have a valid job reference");
        }
        
        try {
            Application saved = applicationRepository.save(application);
            logger.info("Application saved with ID: {}", saved.getId());
            return saved;
        } catch (Exception e) {
            logger.error("Error saving application", e);
            throw e;
        }
    }
    
    public void deleteApplication(UUID id) {
        logger.info("Deleting application with ID: {}", id);
        applicationRepository.deleteById(id);
    }
} 