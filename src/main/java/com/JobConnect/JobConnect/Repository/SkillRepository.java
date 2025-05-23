package com.JobConnect.JobConnect.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JobConnect.JobConnect.Model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID> {
    List<Skill> findByNameContainingIgnoreCase(String query);
} 