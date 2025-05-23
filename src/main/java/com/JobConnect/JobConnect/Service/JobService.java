package com.JobConnect.JobConnect.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
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
    
    /**
     * Get the company_id for a job directly from the database
     * @param jobId The UUID of the job
     * @return The UUID of the company, or null if not found
     */
    public UUID getCompanyIdForJob(UUID jobId) {
        try {
            String sql = "SELECT company_id FROM jobs WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, UUID.class, jobId);
        } catch (Exception e) {
            // Return null if company_id is not found or any other error occurs
            return null;
        }
    }
} 