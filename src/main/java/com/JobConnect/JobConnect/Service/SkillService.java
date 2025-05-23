package com.JobConnect.JobConnect.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobConnect.JobConnect.Model.Skill;
import com.JobConnect.JobConnect.Repository.SkillRepository;

@Service
public class SkillService {
    
    @Autowired
    private SkillRepository skillRepository;
    
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
    
    public Optional<Skill> getSkillById(UUID id) {
        return skillRepository.findById(id);
    }
    
    public List<Skill> searchSkillsByName(String query) {
        return skillRepository.findByNameContainingIgnoreCase(query);
    }
    
    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }
    
    public void deleteSkill(UUID id) {
        skillRepository.deleteById(id);
    }
} 