package com.JobConnect.JobConnect.Model;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "experiences")
public class Experience {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String companyName;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    @JsonIgnoreProperties({"experiences", "applications", "skills"})
    private Applicant applicant;

    public Experience() {}

    public Experience(String companyName, String role,
                      LocalDate start, LocalDate end) {
        this.companyName = companyName;
        this.role = role;
        this.startDate = start;
        this.endDate = end;
    }

    public UUID getId() { return id; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String n) { this.companyName = n; }
    public String getRole() { return role; }
    public void setRole(String r) { this.role = r; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate d) { this.startDate = d; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate d) { this.endDate = d; }

    public Applicant getApplicant() { return applicant; }
    public void setApplicant(Applicant a) { this.applicant = a; }
}
