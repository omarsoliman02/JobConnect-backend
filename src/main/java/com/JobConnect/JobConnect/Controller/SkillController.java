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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JobConnect.JobConnect.Model.Skill;
import com.JobConnect.JobConnect.Service.SkillService;

@RestController
@RequestMapping("/api/skills")
public class SkillController {
    
    @Autowired
    private SkillService skillService;
    
    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        List<Skill> skills = skillService.getAllSkills();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Skill>> searchSkills(@RequestParam String query) {
        List<Skill> skills = skillService.searchSkillsByName(query);
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        Skill savedSkill = skillService.saveSkill(skill);
        return new ResponseEntity<>(savedSkill, HttpStatus.CREATED);
    }
    
    @GetMapping("/{skillId}")
    public ResponseEntity<Skill> getSkillById(@PathVariable UUID skillId) {
        Optional<Skill> skill = skillService.getSkillById(skillId);
        return skill.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{skillId}")
    public ResponseEntity<Skill> updateSkill(@PathVariable UUID skillId, @RequestBody Skill skillDetails) {
        Optional<Skill> optionalSkill = skillService.getSkillById(skillId);
        if (optionalSkill.isPresent()) {
            Skill skill = optionalSkill.get();
            skill.setName(skillDetails.getName());
            Skill updatedSkill = skillService.saveSkill(skill);
            return new ResponseEntity<>(updatedSkill, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(@PathVariable UUID skillId) {
        Optional<Skill> skill = skillService.getSkillById(skillId);
        if (skill.isPresent()) {
            skillService.deleteSkill(skillId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} 