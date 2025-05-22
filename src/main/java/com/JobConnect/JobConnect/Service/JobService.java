package com.JobConnect.JobConnect.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobConnect.JobConnect.Model.Application;
import com.JobConnect.JobConnect.Model.Job;
import com.JobConnect.JobConnect.Repository.ApplicationRepository;
import com.JobConnect.JobConnect.Repository.JobRepository;

@Service
public class JobService {
    
    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
    
    public Optional<Job> getJobById(UUID id) {
        return jobRepository.findById(id);
    }
    
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }
    
    public void deleteJob(UUID id) {
        jobRepository.deleteById(id);
    }
    
    public List<Application> getApplicationsByJobId(UUID jobId) {
        return applicationRepository.findByJobId(jobId);
    }
} 