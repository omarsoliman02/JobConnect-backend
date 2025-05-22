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

import com.JobConnect.JobConnect.Model.Experience;
import com.JobConnect.JobConnect.Service.ExperienceService;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {
    
    @Autowired
    private ExperienceService experienceService;
    
    @GetMapping
    public ResponseEntity<List<Experience>> getAllExperiences() {
        List<Experience> experiences = experienceService.getAllExperiences();
        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Experience> createExperience(@RequestBody Experience experience) {
        Experience savedExperience = experienceService.saveExperience(experience);
        return new ResponseEntity<>(savedExperience, HttpStatus.CREATED);
    }
    
    @GetMapping("/{experienceId}")
    public ResponseEntity<Experience> getExperienceById(@PathVariable UUID experienceId) {
        Optional<Experience> experience = experienceService.getExperienceById(experienceId);
        return experience.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{experienceId}")
    public ResponseEntity<Experience> updateExperience(@PathVariable UUID experienceId, @RequestBody Experience experienceDetails) {
        Optional<Experience> optionalExperience = experienceService.getExperienceById(experienceId);
        if (optionalExperience.isPresent()) {
            Experience experience = optionalExperience.get();
            experience.setCompanyName(experienceDetails.getCompanyName());
            experience.setRole(experienceDetails.getRole());
            experience.setStartDate(experienceDetails.getStartDate());
            experience.setEndDate(experienceDetails.getEndDate());
            Experience updatedExperience = experienceService.saveExperience(experience);
            return new ResponseEntity<>(updatedExperience, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{experienceId}")
    public ResponseEntity<Void> deleteExperience(@PathVariable UUID experienceId) {
        Optional<Experience> experience = experienceService.getExperienceById(experienceId);
        if (experience.isPresent()) {
            experienceService.deleteExperience(experienceId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} 