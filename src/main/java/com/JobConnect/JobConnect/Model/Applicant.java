package com.JobConnect.JobConnect.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Applicant {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "applicant",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @JsonManagedReference(value = "applicant-applications")
    private List<Application> applications = new ArrayList<>();

    @OneToMany(mappedBy = "applicant",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @JsonManagedReference(value = "applicant-experiences")
    private List<Experience> experiences = new ArrayList<>();

    @ManyToMany
    @JoinTable(
      name = "applicant_skill",
      joinColumns = @JoinColumn(name = "applicant_id"),
      inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();

    public Applicant() {}

    public Applicant(String fn, String ln, String email, String phone) {
        this.firstName = fn;
        this.lastName = ln;
        this.email = email;
        this.phone = phone;
    }

    public UUID getId() { return id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String fn) { this.firstName = fn; }
    public String getLastName() { return lastName; }
    public void setLastName(String ln) { this.lastName = ln; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getPhone() { return phone; }
    public void setPhone(String p) { this.phone = p; }

    public List<Application> getApplications() { return applications; }
    public void addApplication(Application app) {
        applications.add(app);
        app.setApplicant(this);
    }
    public void removeApplication(Application app) {
        applications.remove(app);
        app.setApplicant(null);
    }

    public List<Experience> getExperiences() { return experiences; }
    public void addExperience(Experience exp) {
        experiences.add(exp);
        exp.setApplicant(this);
    }
    public void removeExperience(Experience exp) {
        experiences.remove(exp);
        exp.setApplicant(null);
    }

    public Set<Skill> getSkills() { return skills; }
    public void addSkill(Skill s) { skills.add(s); }
    public void removeSkill(Skill s) { skills.remove(s); }
}
