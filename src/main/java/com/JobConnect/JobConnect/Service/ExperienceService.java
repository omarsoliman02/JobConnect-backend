package com.JobConnect.JobConnect.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobConnect.JobConnect.Model.Experience;
import com.JobConnect.JobConnect.Repository.ExperienceRepository;

@Service
public class ExperienceService {
    
    @Autowired
    private ExperienceRepository experienceRepository;
    
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }
    
    public Optional<Experience> getExperienceById(UUID id) {
        return experienceRepository.findById(id);
    }
    
    public Experience saveExperience(Experience experience) {
        return experienceRepository.save(experience);
    }
    
    public void deleteExperience(UUID id) {
        experienceRepository.deleteById(id);
    }
} 