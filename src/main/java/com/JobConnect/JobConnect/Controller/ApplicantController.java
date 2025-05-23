package com.JobConnect.JobConnect.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JobConnect.JobConnect.Model.Applicant;
import com.JobConnect.JobConnect.Model.Application;
import com.JobConnect.JobConnect.Model.Experience;
import com.JobConnect.JobConnect.Service.ApplicantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/applicants", 
                produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Applicant", description = "Applicant management APIs")
public class ApplicantController {
    
    @Autowired
    private ApplicantService applicantService;
    
    @GetMapping
    @Operation(summary = "Get all applicants", description = "Retrieve a list of all applicants")
    @ApiResponse(responseCode = "200", description = "Applicants found", 
                 content = @Content(schema = @Schema(implementation = Applicant.class)))
    public ResponseEntity<List<Applicant>> getAllApplicants() {
        List<Applicant> applicants = applicantService.getAllApplicants();
        return new ResponseEntity<>(applicants, HttpStatus.OK);
    }
    
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @Operation(summary = "Create an applicant", description = "Add a new applicant to the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Applicant created", 
                     content = @Content(schema = @Schema(implementation = Applicant.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<Applicant> createApplicant(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Applicant object that needs to be created",
                required = true,
                content = @Content(schema = @Schema(implementation = Applicant.class))
            )
            @RequestBody Applicant applicant) {
        // Clear any provided ID - let the database generate it
        applicant.setId(null);
        
        // Clear relationships that should be established separately
        applicant.setApplications(null);
        applicant.setExperiences(null);
        applicant.setSkills(null);
        
        // Save the basic applicant
        Applicant savedApplicant = applicantService.saveApplicant(applicant);
        return new ResponseEntity<>(savedApplicant, HttpStatus.CREATED);
    }
    
    @GetMapping("/{applicantId}")
    @Operation(summary = "Get applicant by ID", description = "Retrieve a specific applicant by its UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Applicant found", 
                     content = @Content(schema = @Schema(implementation = Applicant.class))),
        @ApiResponse(responseCode = "404", description = "Applicant not found", content = @Content)
    })
    public ResponseEntity<Applicant> getApplicantById(
            @Parameter(description = "UUID of the applicant to retrieve", required = true)
            @PathVariable UUID applicantId) {
        Optional<Applicant> applicant = applicantService.getApplicantById(applicantId);
        return applicant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(path = "/{applicantId}", consumes = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @Operation(summary = "Update applicant", description = "Update an existing applicant by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Applicant updated", 
                     content = @Content(schema = @Schema(implementation = Applicant.class))),
        @ApiResponse(responseCode = "404", description = "Applicant not found", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<Applicant> updateApplicant(
            @Parameter(description = "UUID of the applicant to update", required = true)
            @PathVariable UUID applicantId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated applicant object",
                required = true,
                content = @Content(schema = @Schema(implementation = Applicant.class))
            )
            @RequestBody Applicant applicantDetails) {
        Optional<Applicant> optionalApplicant = applicantService.getApplicantById(applicantId);
        if (optionalApplicant.isPresent()) {
            Applicant applicant = optionalApplicant.get();
            applicant.setFirstName(applicantDetails.getFirstName());
            applicant.setLastName(applicantDetails.getLastName());
            applicant.setEmail(applicantDetails.getEmail());
            applicant.setPhone(applicantDetails.getPhone());
            Applicant updatedApplicant = applicantService.saveApplicant(applicant);
            return new ResponseEntity<>(updatedApplicant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{applicantId}")
    @Operation(summary = "Delete applicant", description = "Delete an applicant by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Applicant deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "Applicant not found", content = @Content)
    })
    public ResponseEntity<Void> deleteApplicant(
            @Parameter(description = "UUID of the applicant to delete", required = true)
            @PathVariable UUID applicantId) {
        Optional<Applicant> applicant = applicantService.getApplicantById(applicantId);
        if (applicant.isPresent()) {
            applicantService.deleteApplicant(applicantId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{applicantId}/applications")
    @Operation(summary = "Get applicant applications", description = "Retrieve all applications for a specific applicant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Applications found", 
                     content = @Content(schema = @Schema(implementation = Application.class))),
        @ApiResponse(responseCode = "404", description = "Applicant not found", content = @Content)
    })
    public ResponseEntity<List<Application>> getApplicationsByApplicantId(
            @Parameter(description = "UUID of the applicant to retrieve applications for", required = true)
            @PathVariable UUID applicantId) {
        Optional<Applicant> applicant = applicantService.getApplicantById(applicantId);
        if (applicant.isPresent()) {
            List<Application> applications = applicantService.getApplicationsByApplicantId(applicantId);
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{applicantId}/experiences")
    @Operation(summary = "Get applicant experiences", description = "Retrieve all experiences for a specific applicant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Experiences found", 
                     content = @Content(schema = @Schema(implementation = Experience.class))),
        @ApiResponse(responseCode = "404", description = "Applicant not found", content = @Content)
    })
    public ResponseEntity<List<Experience>> getExperiencesByApplicantId(
            @Parameter(description = "UUID of the applicant to retrieve experiences for", required = true)
            @PathVariable UUID applicantId) {
        Optional<Applicant> applicant = applicantService.getApplicantById(applicantId);
        if (applicant.isPresent()) {
            List<Experience> experiences = applicantService.getExperiencesByApplicantId(applicantId);
            return new ResponseEntity<>(experiences, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} 