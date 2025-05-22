package com.JobConnect.JobConnect.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobConnect.JobConnect.Model.Application;
import com.JobConnect.JobConnect.Repository.ApplicationRepository;

@Service
public class ApplicationService {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
    
    public Optional<Application> getApplicationById(UUID id) {
        return applicationRepository.findById(id);
    }
    
    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }
    
    public void deleteApplication(UUID id) {
        applicationRepository.deleteById(id);
    }
} 