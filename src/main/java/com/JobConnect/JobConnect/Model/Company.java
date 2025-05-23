package com.JobConnect.JobConnect.Model;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "companies")
@Schema(description = "Company entity representing an employer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Company {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Schema(description = "Unique identifier of the company", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Name of the company", example = "Tech Solutions Inc.")
    private String name;

    @OneToMany(mappedBy = "company",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @Schema(description = "List of jobs posted by this company")
    private List<Job> jobs = new ArrayList<>();

    public Company() {}

    public Company(String name) {
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Job> getJobs() { return jobs; }
    public void addJob(Job job) {
        jobs.add(job);
        job.setCompany(this);
    }
    public void removeJob(Job job) {
        jobs.remove(job);
        job.setCompany(null);
    }
}

