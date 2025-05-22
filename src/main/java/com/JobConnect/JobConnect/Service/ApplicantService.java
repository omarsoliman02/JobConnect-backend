package com.JobConnect.JobConnect.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobConnect.JobConnect.Model.Applicant;
import com.JobConnect.JobConnect.Model.Application;
import com.JobConnect.JobConnect.Model.Experience;
import com.JobConnect.JobConnect.Repository.ApplicantRepository;
import com.JobConnect.JobConnect.Repository.ApplicationRepository;
import com.JobConnect.JobConnect.Repository.ExperienceRepository;

@Service
public class ApplicantService {
    
    @Autowired
    private ApplicantRepository applicantRepository;
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private ExperienceRepository experienceRepository;
    
    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }
    
    public Optional<Applicant> getApplicantById(UUID id) {
        return applicantRepository.findById(id);
    }
    
    public Applicant saveApplicant(Applicant applicant) {
        return applicantRepository.save(applicant);
    }
    
    public void deleteApplicant(UUID id) {
        applicantRepository.deleteById(id);
    }
    
    public List<Application> getApplicationsByApplicantId(UUID applicantId) {
        return applicationRepository.findByApplicantId(applicantId);
    }
    
    public List<Experience> getExperiencesByApplicantId(UUID applicantId) {
        return experienceRepository.findByApplicantId(applicantId);
    }
} 