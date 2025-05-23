package com.JobConnect.JobConnect.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "jobs")
@Schema(description = "Job entity representing an employment opportunity")
public class Job {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Schema(description = "Unique identifier of the job", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Job title", example = "Senior Java Developer")
    private String title;
    
    @Column(columnDefinition = "TEXT")
    @Schema(description = "Detailed job description", example = "We are looking for a Java developer with 5+ years of experience...")
    private String description;
    
    @Schema(description = "Job location", example = "Paris, France")
    private String location;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Job type", example = "FULL_TIME", allowableValues = {"FULL_TIME", "PART_TIME", "CONTRACT", "FREELANCE", "INTERNSHIP"})
    private JobType jobType;

    @Schema(description = "Minimum salary", example = "50000")
    private Integer minSalary;

    @Schema(description = "Maximum salary", example = "80000")
    private Integer maxSalary;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Experience level required", example = "INTERMEDIATE", allowableValues = {"ENTRY_LEVEL", "INTERMEDIATE", "SENIOR", "EXECUTIVE"})
    private ExperienceLevel experienceLevel;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @Schema(description = "Date and time when the job was created")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Schema(description = "Company offering this job")
    private Company company;

    @OneToMany(mappedBy = "job",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @JsonIgnoreProperties({"job", "applicant"})
    @Schema(description = "List of applications for this job")
    private List<Application> applications = new ArrayList<>();

    public Job() {}

    public Job(String title, String description, String location) {
        this.title = title;
        this.description = description;
        this.location = location;
    }
    
    public Job(String title, String description, String location, JobType jobType, 
               Integer minSalary, Integer maxSalary, ExperienceLevel experienceLevel) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.experienceLevel = experienceLevel;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String t) { this.title = t; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public String getLocation() { return location; }
    public void setLocation(String l) { this.location = l; }

    public JobType getJobType() { return jobType; }
    public void setJobType(JobType jobType) { this.jobType = jobType; }

    public Integer getMinSalary() { return minSalary; }
    public void setMinSalary(Integer minSalary) { this.minSalary = minSalary; }

    public Integer getMaxSalary() { return maxSalary; }
    public void setMaxSalary(Integer maxSalary) { this.maxSalary = maxSalary; }

    public ExperienceLevel getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(ExperienceLevel experienceLevel) { this.experienceLevel = experienceLevel; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public Company getCompany() { return company; }
    public void setCompany(Company c) { this.company = c; }

    public List<Application> getApplications() { return applications; }
    public void addApplication(Application app) {
        applications.add(app);
        app.setJob(this);
    }
    public void removeApplication(Application app) {
        applications.remove(app);
        app.setJob(null);
    }
}
