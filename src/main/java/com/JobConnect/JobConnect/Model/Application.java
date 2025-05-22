package com.JobConnect.JobConnect.Model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    @JsonBackReference(value = "applicant-applications")
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    @JsonBackReference(value = "job-applications")
    private Job job;

    private LocalDateTime appliedAt = LocalDateTime.now();

    public Application() {}

    public Application(Applicant ap, Job jb) {
        this.applicant = ap;
        this.job = jb;
    }

    public UUID getId() { return id; }
    public Applicant getApplicant() { return applicant; }
    public void setApplicant(Applicant a) { this.applicant = a; }
    public Job getJob() { return job; }
    public void setJob(Job j) { this.job = j; }
    public LocalDateTime getAppliedAt() { return appliedAt; }
}
