package com.JobConnect.JobConnect.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "applicants")
@Schema(description = "Applicant entity representing a job seeker")
public class Applicant {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Schema(description = "Unique identifier of the applicant", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Applicant's first name", example = "John")
    private String firstName;
    
    @Schema(description = "Applicant's last name", example = "Doe")
    private String lastName;
    
    @Schema(description = "Applicant's email address", example = "john.doe@example.com")
    private String email;
    
    @Schema(description = "Applicant's phone number", example = "+33 6 12 34 56 78")
    private String phone;

    @OneToMany(mappedBy = "applicant",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @JsonIgnoreProperties({"applicant", "job"})
    @Schema(description = "List of job applications submitted by this applicant")
    private List<Application> applications = new ArrayList<>();

    @OneToMany(mappedBy = "applicant",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @JsonIgnoreProperties("applicant")
    @Schema(description = "List of work experiences for this applicant")
    private List<Experience> experiences = new ArrayList<>();

    @ManyToMany
    @JoinTable(
      name = "applicant_skill",
      joinColumns = @JoinColumn(name = "applicant_id"),
      inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Schema(description = "Set of skills possessed by this applicant")
    private Set<Skill> skills = new HashSet<>();

    public Applicant() {}

    public Applicant(String fn, String ln, String email, String phone) {
        this.firstName = fn;
        this.lastName = ln;
        this.email = email;
        this.phone = phone;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String fn) { this.firstName = fn; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String ln) { this.lastName = ln; }
    
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    
    public String getPhone() { return phone; }
    public void setPhone(String p) { this.phone = p; }

    public List<Application> getApplications() { return applications; }
    public void setApplications(List<Application> applications) { 
        this.applications.clear();
        if (applications != null) {
            this.applications.addAll(applications);
        }
    }
    
    public void addApplication(Application app) {
        applications.add(app);
        app.setApplicant(this);
    }
    
    public void removeApplication(Application app) {
        applications.remove(app);
        app.setApplicant(null);
    }

    public List<Experience> getExperiences() { return experiences; }
    public void setExperiences(List<Experience> experiences) {
        this.experiences.clear();
        if (experiences != null) {
            this.experiences.addAll(experiences);
        }
    }
    
    public void addExperience(Experience exp) {
        experiences.add(exp);
        exp.setApplicant(this);
    }
    
    public void removeExperience(Experience exp) {
        experiences.remove(exp);
        exp.setApplicant(null);
    }

    public Set<Skill> getSkills() { return skills; }
    public void setSkills(Set<Skill> skills) {
        this.skills.clear();
        if (skills != null) {
            this.skills.addAll(skills);
        }
    }
    
    public void addSkill(Skill s) { skills.add(s); }
    public void removeSkill(Skill s) { skills.remove(s); }
}
